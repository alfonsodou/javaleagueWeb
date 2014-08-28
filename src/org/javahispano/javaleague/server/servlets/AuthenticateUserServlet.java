/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.AppUser;
import org.javahispano.javaleague.server.domain.TacticUser;
import org.javahispano.javaleague.server.service.AppUserDao;
import org.javahispano.javaleague.server.service.TacticUserDao;
import org.javahispano.javaleague.server.utils.LoginHelper;

/**
 * @author adou
 * 
 */
public class AuthenticateUserServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(AuthenticateUserServlet.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AppUserDao appUserDao = new AppUserDao();
	private TacticUserDao tacticUserDao = new TacticUserDao();

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		AppUser appUserTemp = null;
		Date now = new Date();
		try {
			appUserTemp = appUserDao.getByProperty2("email",
					req.getParameter("email"));
			if ((appUserTemp != null)
					&& (req.getParameter("token")
							.equals(appUserTemp.getToken()))
					&& (now.compareTo(addMinutesToDate(
							appUserTemp.getDateToken(), 3600)) < 0)) {
				appUserTemp.setActive(true);
				appUserTemp.setLastActive(new Date());
				appUserTemp.setLastLoginOn(new Date());
				TacticUser tacticUser = new TacticUser(appUserTemp.getId(),
						req.getParameter("teamName"));
				tacticUserDao.save(tacticUser);
				appUserTemp.setTacticUserId(tacticUser.getId());
				
				appUserDao.save(appUserTemp);
				


				/*
				 * All done.
				 */
				resp.sendRedirect(LoginHelper.getApplicationURL(req)
						+ "/#WelcomePlace:null");
				/*
				 * resp.sendRedirect(LoginHelper.getApplicationURL(req) +
				 * "/authuser.jsp");
				 */

			} else {
				resp.sendRedirect(LoginHelper.getApplicationURL(req)
						+ "/errorauthuser.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.warning(e.getMessage());

		}
	}

	/**
	 * Agrega o quita minutos a una fecha dada. Para quitar minutos hay que
	 * sumarle valores negativos.
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinutesToDate(Date date, int minutes) {
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		calendarDate.add(Calendar.MINUTE, minutes);
		return calendarDate.getTime();
	}
}
