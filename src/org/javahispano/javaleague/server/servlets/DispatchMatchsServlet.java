/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.LeagueDao;
import org.javahispano.javaleague.server.domain.MatchLeagueDao;
import org.javahispano.javaleague.server.utils.Utils;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.MatchLeague;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * @author adou
 *
 */
public class DispatchMatchsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(DispatchMatchsServlet.class.getName());
	private static Queue queue = QueueFactory.getQueue("league");
	private LeagueDao leagueDao = new LeagueDao();
	private MatchLeagueDao matchLeagueDao = new MatchLeagueDao();

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		try {
			List<MatchLeague> matchs = new ArrayList<MatchLeague>();
			matchs = matchLeagueDao.findAllByLeague(leagueDao
					.findDefaultLeague().getId());
			for (MatchLeague m : matchs) {

				queue.add(TaskOptions.Builder.withUrl("/playMatchLeague").param(
						"matchID", m.getId().toString()));
				
				m.setState(AppLib.MATCH_QUEUE);
				matchLeagueDao.save(m);
			}
		} catch (Exception e) {
			log.warning(Utils.stackTraceToString(e));
		}
	}

}
