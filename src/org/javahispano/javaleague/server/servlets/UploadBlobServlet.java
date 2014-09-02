/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.javahispano.javaleague.javacup.shared.Agent;
import org.javahispano.javaleague.server.classloader.MyDataStoreClassLoader;
import org.javahispano.javaleague.server.domain.AppUserDao;
import org.javahispano.javaleague.server.domain.FrameWorkDao;
import org.javahispano.javaleague.server.domain.TacticUserDao;
import org.javahispano.javaleague.server.utils.Utils;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.FrameWork;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
 * @author adou
 * 
 */
public class UploadBlobServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());
	private static final Logger log = Logger.getLogger(UploadBlobServlet.class
			.getName());
	private static TacticUserDao tacticUserDao = new TacticUserDao();
	private static AppUserDao appUserDao = new AppUserDao();
	private static FrameWorkDao frameWorkDAO = new FrameWorkDao();

	private MyDataStoreClassLoader myDataStoreClassLoader;
	
	private String stackTrace;
	private Object objectTactic;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		TacticUser tacticUser = null;
		String status = null;
		GcsFilename fileName = null;
		byte[] zipBytes = null;
		int error = 0;
		String teamName = null;

		stackTrace = null;
		objectTactic = null;
		try {
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iter = upload.getItemIterator(req);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				if (item.isFormField()) {
					if (item.getFieldName().equals("tacticUserId")) {
						tacticUser = tacticUserDao.fetch(Long.getLong(IOUtils
								.toString(item.openStream())));
					} else if (item.getFieldName().equals("teamName")) {
						teamName = IOUtils.toString(item.openStream());
					}
				}
			}
			iter = upload.getItemIterator(req);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				if (!item.isFormField()) {
					if (!item.getName().isEmpty()) {
						zipBytes = IOUtils.toByteArray(item.openStream());
						if (zipBytes != null) {
							error = validateTactic(zipBytes, tacticUser.getId()
									.toString());
							if (error == 0) {
								fileName = new GcsFilename(AppLib.BUCKET_GCS,
										"user/"
												+ tacticUser.getUserId()
														.toString() + "/"
												+ tacticUser.getId().toString()
												+ "/" + item.getName());
								writeToFile(fileName, zipBytes);

								if (tacticUser.getFileNameJar() != null) {
									gcsService.delete(new GcsFilename(
											AppLib.BUCKET_GCS, "user/"
													+ tacticUser.getUserId()
															.toString()
													+ "/"
													+ tacticUser.getId()
															.toString()
													+ "/"
													+ tacticUser
															.getFileNameJar()));
								}

								tacticUser.setFileNameJar(item.getName());
								tacticUser.setBytes(gcsService.getMetadata(
										fileName).getLength());
								if (tacticUser.getState() != AppLib.FRIENDLY_MATCH_SCHEDULED) {
									tacticUser
											.setState(AppLib.FRIENDLY_MATCH_OK);
								}
							}
						}
					}
				}
			}

			if (error == 0) {
				tacticUser.setUpdated(new Date());
				tacticUser.setTeamName(teamName);
				tacticUserDao.save(tacticUser);
			}

			PrintWriter out = res.getWriter();
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version='1.0' encoding='ISO-8859-1'?>\n");
			sb.append("<root>");
			sb.append("<tactic>");
			sb.append("<teamname>" + tacticUser.getTeamName() + "</teamname>\n");
			sb.append("<filename>" + tacticUser.getFileNameJar()
					+ "</filename>\n");
			sb.append("<bytes>" + tacticUser.getBytes().toString()
					+ "</bytes>\n");
			sb.append("<error>" + Integer.toString(error) + "</error>");
			sb.append("<stacktrace>" + stackTrace + "</stacktrace>");
			sb.append("</tactic>");
			sb.append("</root>");
			log.warning("XML: " + sb.toString());
			out.println(sb.toString());
			out.flush();

		} catch (Exception e) {
			log.warning(Utils.stackTraceToString(e));
		}

	}

	private void writeToFile(GcsFilename fileName, byte[] content)
			throws IOException {
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName,
				GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}

	private int validateTactic(byte[] tactic, String tacticId) {
		int result = 0;
		FrameWork frameWork = null;

		try {
			myDataStoreClassLoader = new MyDataStoreClassLoader(this.getClass()
					.getClassLoader());

			frameWork = frameWorkDAO.findById(AppLib.DEFAULT_FRAMEWORK_ID);
			// Cargamos el framework
			myDataStoreClassLoader.addClassJarFramework(new BlobKey(frameWork
					.getFrameWork()));

			Class<? extends Agent> cz = Class.forName(
					"org.javahispano.javacup.model.engine.AgentPartido", true,
					myDataStoreClassLoader).asSubclass(Agent.class);

			Agent a = cz.newInstance();

			result = loadClass(tactic, a, AppLib.PATH_PACKAGE + tacticId);
			
			// Realizamos la última comprobación
			// Ejecutar las primeras iteraciones de un partido
			if (result == 0)  {
				stackTrace = a.testTactic(objectTactic, objectTactic);
				if (stackTrace != null) {
					result = 3;
				}
			}

		} catch (Exception e) {
			result = 1;
			log.warning(Utils.stackTraceToString(e));
		}

		return result;
	}

	private int loadClass(byte[] tactic, Agent a, String packagePath)
			throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<?> cz = null;
		Class<?> result = null;
		Map<String, byte[]> byteStream;
		boolean errorPackageName, existInterfaceTactic;
		int errorValidate = 0;

		byteStream = myDataStoreClassLoader.addClassJar(tactic);

		Iterator it = byteStream.entrySet().iterator();
		errorPackageName = false;
		while (it.hasNext() && !errorPackageName) {

			try {
				Map.Entry e = (Map.Entry) it.next();

				String name = new String((String) e.getKey());

				if (!name.contains(packagePath)) {
					errorPackageName = true;
				} else {
					myDataStoreClassLoader
							.addClass(name, (byte[]) e.getValue());
				}

			} catch (Exception e) {
				log.warning(Utils.stackTraceToString(e));
			}

		}

		Iterator it1 = byteStream.entrySet().iterator();
		existInterfaceTactic = false;
		while (!errorPackageName && it1.hasNext() && !existInterfaceTactic) {

			try {
				Map.Entry e = (Map.Entry) it1.next();

				String name = new String((String) e.getKey());

				cz = myDataStoreClassLoader.loadClass(name);

				if (a.isTactic(cz)) {
					objectTactic = cz.newInstance();
					existInterfaceTactic = true;
				}
			} catch (Exception e) {
				log.warning(Utils.stackTraceToString(e));
			}

		}

		if (errorPackageName == true) {
			errorValidate = 1;
		} else if (existInterfaceTactic == false) {
			errorValidate = 2;
		}

		return errorValidate;

	}

}
