/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.MatchFriendlyDao;
import org.javahispano.javaleague.shared.domain.MatchFriendly;

/**
 * @author adou
 * 
 */
public class TimeTacticMatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(TimeTacticMatchServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		MatchFriendlyDao matchDAO = new MatchFriendlyDao();
		MatchFriendly match = null;
		long[] time = null;
		String result = "";
		Long tactic = 0L;

		try {
			match = matchDAO.findById(Long.parseLong(req.getParameter("id")
					.replace("_", "")));
			tactic = Long
					.parseLong(req.getParameter("tactic").replace("_", ""));
		} catch (Exception e) {
			log.warning(e.getMessage());
		}

		res.setContentType("text/plain");
		res.setHeader("ETag", match.getId().toString());// Establece header ETag

		PrintWriter out = res.getWriter();
		if (tactic.equals(match.getLocalTeamId())) {
			time = match.getTimeLocal();
			res.setHeader("Content-disposition",
					"attachment; filename=" + match.getId().toString() + "_"
							+ eliminaBlancos(match.getNameLocal()) + ".csv");
		} else if (tactic.equals(match.getVisitingTeamId())) {
			time = match.getTimeVisita();
			res.setHeader("Content-disposition",
					"attachment; filename=" + match.getId().toString() + "_"
							+ eliminaBlancos(match.getNameForeign()) + ".csv");
		}

		result = "BenchMark=" + match.getBenchMark() + " :: MaxTimeIter="
				+ match.getMaxTimeIter() + "\n";
		for (int i = 0; i < time.length; i++) {
			result += time[i] + "\n";
		}
		result = result.substring(0, result.length() - 1);
		out.println(result);
		res.flushBuffer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}

	private String eliminaBlancos(String sTexto) {
		StringTokenizer stTexto = new StringTokenizer(sTexto);
		String sCadenaSinBlancos = "";

		while (stTexto.hasMoreElements())
			sCadenaSinBlancos += stTexto.nextElement();

		return sCadenaSinBlancos;
	}
}
