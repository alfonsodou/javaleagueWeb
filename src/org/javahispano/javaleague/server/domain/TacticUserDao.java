/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import java.util.logging.Logger;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import org.javahispano.javaleague.shared.domain.AppUser;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * @author adou
 *
 */
public class TacticUserDao {

	private static Logger logger = Logger.getLogger(TacticUserDao.class
			.getName());

	public TacticUserDao() {
		super();
	}

	public void save(TacticUser tacticUser) {
		ofy().save().entity(tacticUser).now();
	}

	public void remove(Long id) {
		try {
			TacticUser tacticUser = fetch(id);
			ofy().delete().entity(tacticUser);
		} catch (Exception e) {
			String message = e.getMessage() + " :: ";
			logger.warning(buildStackTrace(e, message));
		}
	}

	public TacticUser fetch(Long id) {
		TacticUser tacticUser = null;
		try {
			tacticUser = ofy().load().type(TacticUser.class).id(id).now();
		} catch (Exception e) {
			String message = e.getMessage() + " :: ID: " + id + " :: ";
			logger.warning(buildStackTrace(e, message));
		}

		return tacticUser;
	}

	public TacticUser findByUserId(Long id) {
		TacticUser tacticUser = null;
		try {
			tacticUser = ofy().load().type(TacticUser.class)
					.filter("userId", id).first().now();
		} catch (Exception e) {
			String message = e.getMessage() + " :: ";
			logger.warning(buildStackTrace(e, message));
		}

		return tacticUser;
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
