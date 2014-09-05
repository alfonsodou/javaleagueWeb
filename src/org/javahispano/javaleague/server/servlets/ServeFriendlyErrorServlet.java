/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
public class ServeFriendlyErrorServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MatchFriendlyDao dao = new MatchFriendlyDao();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		long id = Long.parseLong(req.getParameter("id").replace("_", ""));
		MatchFriendly p = dao.findById(id);

		res.setHeader("ETag", p.getId().toString());// Establece header ETag
		res.setHeader("Content-disposition", "attachment; filename="
				+ p.getId().toString() + "_stacktrace.txt");
		PrintWriter out = res.getWriter();
		out.println(p.getError());
		res.flushBuffer();
	}

}
