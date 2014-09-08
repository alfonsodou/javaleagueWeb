/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.server.utils.Utils;
import org.javahispano.javaleague.shared.domain.TacticUser;

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
		try {
			ofy().save().entity(tacticUser).now();
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}
	}

	public void remove(Long id) {
		try {
			TacticUser tacticUser = fetch(id);
			ofy().delete().entity(tacticUser);
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}
	}

	public TacticUser fetch(Long id) {
		TacticUser tacticUser = null;
		try {
			tacticUser = ofy().load().type(TacticUser.class).id(id).now();
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}

		return tacticUser;
	}

	public TacticUser findByUserId(Long id) {
		TacticUser tacticUser = null;
		try {
			tacticUser = ofy().load().type(TacticUser.class)
					.filter("userId", id).first().now();
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}

		return tacticUser;
	}

	public List<TacticUser> finbByState(Integer state) {
		try {
			return ofy().load().type(TacticUser.class).filter("state", state)
					.list();
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}
		return null;
	}
}
