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
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.MatchLeague;

/**
 * @author adou
 * 
 */
public class MatchLeagueDao {
	private static Logger logger = Logger.getLogger(MatchLeagueDao.class
			.getName());

	public MatchLeagueDao() {
		super();
	}

	public MatchLeague save(MatchLeague matchLeague) {
		try {
			matchLeague.setUpdated(new Date());
			ofy().save().entity(matchLeague).now();
			return matchLeague;
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}
		return null;
	}

	public MatchLeague findById(Long id) {
		try {
			return ofy().load().type(MatchLeague.class).id(id).now();
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}
		return null;
	}

	public List<MatchLeague> findAllByTactic(Long tacticId) {
		try {
			List<MatchLeague> matchs = new ArrayList<MatchLeague>();

			matchs.addAll(ofy().load().type(MatchLeague.class)
					.filter("localTeamId", tacticId).order("visualization")
					.list());

			matchs.addAll(ofy().load().type(MatchLeague.class)
					.filter("visitingTeamId", tacticId).order("visualization")
					.list());

			return matchs;
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}
		return null;
	}

	public List<MatchLeague> findAllByLeague(Long leagueId) {
		try {
			List<MatchLeague> matchs = new ArrayList<MatchLeague>();

			matchs.addAll(ofy().load().type(MatchLeague.class)
					.filter("leagueId", leagueId)
					.filter("state", AppLib.MATCH_SCHEDULED).order("execution")
					.list());

			return matchs;
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}
		return null;
	}

	public List<MatchLeague> getMatchsState(int matchLeagueWaiting) {
		try {
			List<MatchLeague> matchs = ofy().load().type(MatchLeague.class)
					.filter("state", matchLeagueWaiting).list();
			return matchs;
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}

		return null;
	}
}
