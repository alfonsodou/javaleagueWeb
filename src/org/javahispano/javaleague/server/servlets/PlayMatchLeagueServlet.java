package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.javacup.shared.Agent;
import org.javahispano.javaleague.javacup.shared.BenchMark;
import org.javahispano.javaleague.javacup.shared.MatchShared;
import org.javahispano.javaleague.server.classloader.MyDataStoreClassLoader;
import org.javahispano.javaleague.server.domain.FrameWorkDao;
import org.javahispano.javaleague.server.domain.MatchLeagueDao;
import org.javahispano.javaleague.server.domain.TacticUserDao;
import org.javahispano.javaleague.server.utils.Utils;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.FrameWork;
import org.javahispano.javaleague.shared.domain.MatchLeague;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

public class PlayMatchLeagueServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(PlayMatchLeagueServlet.class.getName());
	private MyDataStoreClassLoader myDataStoreClassLoader;
	private MatchLeagueDao matchDAO = new MatchLeagueDao();
	private TacticUserDao tacticUserDAO = new TacticUserDao();
	private FrameWorkDao frameWorkDAO = new FrameWorkDao();

	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());

	private GcsFilename filename;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		// Partido partido = null;

		MatchLeague match = null;
		Object lo = null;
		Object vo = null;
		FrameWork frameWork = null;

		Long matchID = Long.parseLong(req.getParameter("matchID").replace("_",
				""));
		log.warning("matchID: " + matchID);

		TacticUser localTactic = null;
		TacticUser visitingTactic = null;

		try {

			match = matchDAO.findById(matchID);
			log.warning("Match: " + match.getVisualization());

			localTactic = tacticUserDAO.fetch(match.getLocalTeamId());
			log.warning("Local: " + localTactic.getTeamName());
			visitingTactic = tacticUserDAO.fetch(match.getVisitingTeamId());
			log.warning("Visitante: " + visitingTactic.getTeamName());
			frameWork = frameWorkDAO.findById(match.getFrameWorkId());
			log.warning("FrameWork: " + frameWork.getName());

			myDataStoreClassLoader = new MyDataStoreClassLoader(this.getClass()
					.getClassLoader());

			// Cargamos el framework
			myDataStoreClassLoader.addClassJarFramework(new BlobKey(frameWork
					.getFrameWork()));

			Class<? extends Agent> cz = Class.forName(AppLib.CLASS_AGENT, true,
					myDataStoreClassLoader).asSubclass(Agent.class);

			Agent a = cz.newInstance();

			// Cargamos las tácticas
			lo = loadClass(localTactic, a);
			vo = loadClass(visitingTactic, a);

			BenchMark benchMark = new BenchMark();

			MatchShared matchShared = a.execute(lo, vo,
					benchMark.getMaxTimeIter());

			filename = new GcsFilename(AppLib.BUCKET_GCS, AppLib.PATH_MATCH
					+ AppLib.PATH_LEAGUE_MATCH + match.getLeagueId().toString()
					+ match.getId().toString() + ".jvc");
			writeToFile(filename, matchShared.getMatch());

			filename = new GcsFilename(AppLib.BUCKET_GCS, AppLib.PATH_MATCH
					+ AppLib.PATH_LEAGUE_MATCH + match.getLeagueId().toString()
					+ match.getId().toString() + ".bin");
			writeToFile(filename, matchShared.getMatchBin());

			match.setLocalGoals(matchShared.getGoalsLocal());
			match.setVisitingTeamGoals(matchShared.getGoalsVisiting());
			match.setLocalPossesion(matchShared.getPosessionLocal());
			match.setState(AppLib.MATCH_OK);
			match.setTimeLocal(matchShared.getTimeLocal());
			match.setTimeVisita(matchShared.getTimeVisita());
			match.setBenchMark(benchMark.getBenchMark());
			match.setMaxTimeIter(benchMark.getMaxTimeIter());

			localTactic.setState(AppLib.FRIENDLY_MATCH_OK);
			visitingTactic.setState(AppLib.FRIENDLY_MATCH_OK);

		} catch (Exception e) {
			log.warning(Utils.stackTraceToString(e));
			match.setState(AppLib.MATCH_ERROR);
			match.setError(Utils.stackTraceToString(e));
			localTactic.setState(AppLib.FRIENDLY_MATCH_OK);
			visitingTactic.setState(AppLib.FRIENDLY_MATCH_OK);
		} finally {
			matchDAO.save(match);
			tacticUserDAO.save(localTactic);
			tacticUserDAO.save(visitingTactic);
		}

	}

	private Object loadClass(TacticUser tactic, Agent a) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<?> cz = null;
		Class<?> result = null;
		Map<String, byte[]> byteStream;

		GcsFilename fileName = new GcsFilename(AppLib.BUCKET_GCS,
				AppLib.PATH_USER + tactic.getUserId().toString() + "/"
						+ tactic.getId().toString() + "/"
						+ tactic.getFileNameJar());

		byteStream = myDataStoreClassLoader.addClassJar(readFile(fileName));

		Iterator it = byteStream.entrySet().iterator();
		while (it.hasNext()) {

			try {
				Map.Entry e = (Map.Entry) it.next();

				String name = new String((String) e.getKey());

				myDataStoreClassLoader.addClass(name, (byte[]) e.getValue());

			} catch (Exception e) {
				log.warning(Utils.stackTraceToString(e));
			}
		}

		Iterator it1 = byteStream.entrySet().iterator();
		while (it1.hasNext()) {

			try {
				Map.Entry e = (Map.Entry) it1.next();

				String name = new String((String) e.getKey());

				cz = myDataStoreClassLoader.loadClass(name);

				if (a.isTactic(cz)) {
					result = cz;
				}
			} catch (Exception e) {
				log.warning(Utils.stackTraceToString(e));
			}
		}

		if (result != null)
			return result.newInstance();

		return null;

	}

	private void writeToFile(GcsFilename fileName, byte[] content)
			throws IOException {
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName,
				GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}

	private byte[] readFile(GcsFilename fileName) {
		int fileSize;
		ByteBuffer result = null;
		try {
			fileSize = (int) gcsService.getMetadata(fileName).getLength();
			result = ByteBuffer.allocate(fileSize);
			try (GcsInputChannel readChannel = gcsService.openReadChannel(
					fileName, 0)) {
				readChannel.read(result);
			}
		} catch (Exception e) {
			log.warning(Utils.stackTraceToString(e));
		}

		return result.array();
	}

}
