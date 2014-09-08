/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.server.utils.Utils;
import org.javahispano.javaleague.shared.domain.MatchFriendly;

/**
 * @author adou
 * 
 */
public class MatchFriendlyDao {
	private static Logger logger = Logger.getLogger(MatchFriendlyDao.class
			.getName());

	public MatchFriendlyDao() {
		super();
	}

	public MatchFriendly save(MatchFriendly matchFriendly) {
		try {
			matchFriendly.setUpdated(new Date());
			ofy().save().entity(matchFriendly).now();
			return matchFriendly;
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}
		return null;
	}

	public MatchFriendly findById(Long id) {
		try {
			return ofy().load().type(MatchFriendly.class).id(id).now();
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}
		return null;
	}

	public List<MatchFriendly> findAllByTactic(Long tacticId) {
		try {
			List<MatchFriendly> matchFriendlys = new ArrayList<MatchFriendly>();
			
			matchFriendlys.addAll(ofy().load().type(MatchFriendly.class)
					.filter("localTeamId", tacticId).order("visualization")
					.list());

			matchFriendlys.addAll(ofy().load().type(MatchFriendly.class)
					.filter("visitingTeamId", tacticId).order("visualization")
					.list());

			return matchFriendlys;
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}
		return null;
	}

	public List<MatchFriendly> getMatchsState(int matchFriendlyWaiting) {
		try {
			List<MatchFriendly> matchs = ofy().load().type(MatchFriendly.class)
					.filter("state", matchFriendlyWaiting).list();
			return matchs;
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}

		return null;
	}
}
