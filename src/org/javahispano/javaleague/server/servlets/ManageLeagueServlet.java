/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.LeagueDao;
import org.javahispano.javaleague.server.utils.Utils;
import org.javahispano.javaleague.shared.domain.League;

/**
 * @author adou
 *
 */
public class ManageLeagueServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LeagueDao leagueDao = new LeagueDao();
	private static final Logger log = Logger
			.getLogger(ManageLeagueServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String action = req.getParameter("action");

		if (action.equals("add")) {
			League league = new League();

			try {
				league.setName(req.getParameter("name"));
				league.setDefaultLeague(Boolean.TRUE);

				leagueDao.save(league);
			} catch (Exception e) {
				log.warning(Utils.stackTraceToString(e));
			}
		}
	}

}
