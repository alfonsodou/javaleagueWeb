/**
 * 
 */
package org.javahispano.javaleague.server.service;

import java.io.StringWriter;
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
import org.javahispano.javaleague.server.domain.AppUser;
import org.javahispano.javaleague.server.utils.ServletUtils;
import org.javahispano.javaleague.server.utils.SessionIdentifierGenerator;
import org.javahispano.javaleague.server.utils.VelocityHelper;
import org.javahispano.javaleague.shared.exception.TooManyResultsException;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * @author adou
 * 
 */
public class AppUserDao extends ObjectifyDao<AppUser> {

	private static Logger logger = Logger.getLogger(AppUserDao.class.getName());

	public void save(AppUser appUser) {
		this.put(appUser);
	}

	public void remove(Long id) {
		try {
			AppUser appUser = this.get(id);
			this.delete(appUser);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public AppUser fetch(Long id) {
		AppUser appUser = null;
		try {
			appUser = this.get(id);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

		return appUser;
	}

	public AppUser findByEmail(String email) {
		AppUser appUser = null;
		try {
			appUser = this.getByProperty("email", email);
		} catch (TooManyResultsException e) {
			e.printStackTrace();
		}

		return appUser;
	}

	public List<AppUser> getAuthUser(Boolean active) {
		return this.listByProperty("active", active);
	}

	public Boolean newUser(AppUser appUser, String teamName) {
		AppUser appUserTemp = null;
		try {
			appUserTemp = this.getByProperty2("email", appUser.getEmail());
			if (appUserTemp == null) {
				SessionIdentifierGenerator userTokenGenerator = new SessionIdentifierGenerator();
				appUser.setDateToken(new Date());
				appUser.setToken(userTokenGenerator.nextSessionId());
				this.put(appUser);

				VelocityContext context = new VelocityContext();
				context.put("username", appUser.getAppUserName());
				context.put("url", ServletUtils.getBaseUrl()
						+ "/authenticateUser?token=" + appUser.getToken()
						+ "&email=" + appUser.getEmail() + "&teamName="
						+ teamName);

				VelocityEngine ve = VelocityHelper.getVelocityEngine();

				// Finds template in WEB-INF/classes
				Template template = ve.getTemplate("emailTemplate_"
						+ appUser.getLocale() + ".vm");

				StringWriter writer = new StringWriter();
				template.merge(context, writer);

				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);

				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("javaleague@gmail.com",
						"Administrador javaLeague"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						appUser.getEmail(), appUser.getAppUserName()));
				msg.setSubject("Bienvenido a javaLeague!");

				msg.setContent(writer.toString(), "text/html; charset=utf-8");
				msg.setSentDate(new Date());

				Transport.send(msg);

				return Boolean.TRUE;
			} else {
				logger.warning("Dirección de correo encontrada: "
						+ appUserTemp.getAppUserName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			String message = e.getMessage() + " :: ";
			logger.warning(buildStackTrace(e, message));
		}

		return Boolean.FALSE;
	}

	public AppUser login(AppUser appUser) {
		AppUser response = findByEmail(appUser.getEmail());
		if ((response != null)
				&& (response.getPassword().equals(appUser.getPassword()))) {
			return response;
		}

		return null;
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
