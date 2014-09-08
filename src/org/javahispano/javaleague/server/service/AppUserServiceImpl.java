/**
 * 
 */
package org.javahispano.javaleague.server.service;

import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.javahispano.javaleague.client.service.AppUserService;
import org.javahispano.javaleague.server.domain.AppUserDao;
import org.javahispano.javaleague.server.utils.LoginHelper;
import org.javahispano.javaleague.server.utils.ServletUtils;
import org.javahispano.javaleague.server.utils.SessionIdentifierGenerator;
import org.javahispano.javaleague.server.utils.Utils;
import org.javahispano.javaleague.server.utils.VelocityHelper;
import org.javahispano.javaleague.shared.domain.AppUser;
import org.javahispano.javaleague.shared.exception.NotLoggedInException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 *
 */
public class AppUserServiceImpl extends RemoteServiceServlet implements
		AppUserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(AppUserServiceImpl.class
			.getName());

	private AppUserDao appUserDao = new AppUserDao();

	@Override
	public AppUser login(AppUser appUser) {
		try {
			AppUser response = appUserDao.findByEmail(appUser.getEmail());
			if ((response != null) && (response.isActive())
					&& (response.getPassword().equals(appUser.getPassword()))) {
				HttpSession session = getThreadLocalRequest().getSession();
				// update session if successful
				session.setAttribute("userId", String.valueOf(response.getId()));
				session.setAttribute("loggedin", true);

				response.setLastLoginOn(new Date());
				response.setLastActive(new Date());
				appUserDao.save(response);

				return response;
			}
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}

		return null;
	}

	@Override
	public Boolean newUser(AppUser appUser, String teamName) {
		AppUser appUserTemp = null;
		try {
			appUserTemp = appUserDao.findByEmail("email");
			if (appUserTemp == null) {
				SessionIdentifierGenerator userTokenGenerator = new SessionIdentifierGenerator();
				appUser.setDateToken(new Date());
				appUser.setToken(userTokenGenerator.nextSessionId());
				appUserDao.save(appUser);

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
			logger.warning(Utils.stackTraceToString(e));
		}

		return Boolean.FALSE;
	}

	@Override
	public void logout() throws NotLoggedInException {
		getThreadLocalRequest().getSession().invalidate();
		throw new NotLoggedInException("Logged out");
	}

	@Override
	public AppUser getLoggedInUser() {
		try {
			return LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}

		return null;
	}

}