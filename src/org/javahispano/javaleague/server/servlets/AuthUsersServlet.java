/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.AppUserDao;
import org.javahispano.javaleague.server.utils.ServletHelper;
import org.javahispano.javaleague.shared.domain.AppUser;

/**
 * @author adou
 * 
 */
public class AuthUsersServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(AuthUsersServlet.class
			.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AppUserDao appUserDao = new AppUserDao();

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		List<AppUser> listAppUser = new ArrayList<AppUser>();
		listAppUser = appUserDao.getAuthUser(Boolean.FALSE);
		if (listAppUser != null) {
			Date now = new Date();
			for (AppUser appUser : listAppUser) {
				log.info("AuthUsersServlet :: comprobando usuario id: " + appUser.getId());
				if (now.compareTo(ServletHelper.addMinutesToDate(
						appUser.getDateToken(), 1440)) < 0) {
					log.info("AuthUsersServlet :: borrando usuario id: " + appUser.getId());
					appUserDao.remove(appUser);
				}
			}
		}
	}

}
