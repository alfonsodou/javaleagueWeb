/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.javahispano.javaleague.server.utils.ServletUtils;
import org.javahispano.javaleague.server.utils.SessionIdentifierGenerator;
import org.javahispano.javaleague.server.utils.VelocityHelper;
import org.javahispano.javaleague.shared.domain.AppUser;

/**
 * @author adou
 * 
 */
public class AppUserDao {

	private static Logger logger = Logger.getLogger(AppUserDao.class.getName());

	public AppUserDao() {
		super();
	}

	public void save(AppUser appUser) {
		ofy().save().entity(appUser).now();
	}

	public void remove(Long id) {
		try {
			AppUser appUser = fetch(id);
			ofy().delete().entity(appUser).now();
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage() + " :: ";
			logger.warning(buildStackTrace(e, message));
		}
	}

	public void remove(AppUser appUser) {
		try {
			ofy().delete().entity(appUser).now();
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage() + " :: ";
			logger.warning(buildStackTrace(e, message));
		}
	}

	public AppUser fetch(Long id) {
		AppUser appUser = null;
		try {
			appUser = ofy().load().type(AppUser.class).id(id).now();
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage() + " :: ";
			logger.warning(buildStackTrace(e, message));
		}

		return appUser;
	}

	public AppUser findByEmail(String email) {
		AppUser appUser = null;
		try {
			appUser = ofy().load().type(AppUser.class).filter("email", email)
					.first().now();
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage() + " :: ";
			logger.warning(buildStackTrace(e, message));
		}

		return appUser;
	}

	public List<AppUser> getAuthUser(Boolean active) {
		List<AppUser> listAppUser = new ArrayList<AppUser>();

		try {

			listAppUser = ofy().load().type(AppUser.class)
					.filter("active", active).list();
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage() + " :: ";
			logger.warning(buildStackTrace(e, message));
		}

		return listAppUser;
	}



	private String buildStackTrace(Throwable t, String log) {
		// return "disabled";
		if (t != null) {
			log += t.getClass().toString();
			log += t.getMessage();
			//
			StackTraceElement[] stackTrace = t.getStackTrace();
			if (stackTrace != null) {
				StringBuffer trace = new StringBuffer();

				for (int i = 0; i < stackTrace.length; i++) {
					trace.append(stackTrace[i].getClassName() + "."
							+ stackTrace[i].getMethodName() + "("
							+ stackTrace[i].getFileName() + ":"
							+ stackTrace[i].getLineNumber());
				}

				log += trace.toString();
			}
			//
			Throwable cause = t.getCause();
			if (cause != null && cause != t) {

				log += buildStackTrace(cause, "CausedBy:\n");

			}
		}
		return log;
	}
}
