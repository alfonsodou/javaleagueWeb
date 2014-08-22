package org.javahispano.javaleague.server.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.javahispano.javaleague.server.domain.AppUser;
import org.javahispano.javaleague.server.service.AppUserDao;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * @author adou
 * 
 */
public class LoginHelper extends RemoteServiceServlet {
	private static final long serialVersionUID = 2888983680310646846L;

	private static Logger logger = Logger
			.getLogger(LoginHelper.class.getName());
	private static final int NUM_RETRIES = 5;
	private static AppUserDao appUserDao = new AppUserDao();	

	static public String getApplicationURL(HttpServletRequest request) {

		if (ServletHelper.isDevelopment(request)) {
			return "http://127.0.0.1:8888/index.html?gwt.codesvr=127.0.0.1:9997";
		} else {
			return ServletUtils.getBaseUrl(request);
		}

	}

	static public AppUser getLoggedInUser(HttpSession session) {

		if (session == null) {
			logger.warning("LoginHelper: getLoggedInUser :: Session is null!");
			return null; // user not logged in
		}
			

		String userId = (String) session.getAttribute("userId");
		if (userId == null) {
			logger.warning("LoginHelper: getLoggedInUser :: userId is null!");
			return null; // user not logged in			
		}


		Long id = Long.parseLong(userId.trim());
		try {
			AppUser u = appUserDao.get(id);
			u.setLastActive(new Date());
			appUserDao.save(u);
			return u;
		} catch (Exception e) {
			logger.warning(e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.warning("stackTrace -> " + sw.toString());
			return null;
		}

	}

	static public boolean isLoggedIn(HttpServletRequest req) {

		if (req == null)
			return false;
		else {
			HttpSession session = req.getSession();
			if (session == null) {
				logger.info("Session is null...");
				return false;
			} else {
				Boolean isLoggedIn = (Boolean) session.getAttribute("loggedin");
				if (isLoggedIn == null) {
					logger.info("Session found, but did not find loggedin attribute in it: user not logged in");
					return false;
				} else if (isLoggedIn) {
					logger.info("User is logged in...");
					return true;
				} else {
					logger.info("User not logged in");
					return false;
				}
			}
		}
	}


}
