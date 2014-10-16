/**
 * 
 */
package org.javahispano.javaleague.server.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.MatchLeagueService;
import org.javahispano.javaleague.server.domain.MatchLeagueDao;
import org.javahispano.javaleague.server.utils.Utils;
import org.javahispano.javaleague.shared.domain.MatchLeague;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 *
 */
public class MatchLeagueServiceImpl extends RemoteServiceServlet implements
		MatchLeagueService {

	private static Logger logger = Logger
			.getLogger(MatchLeagueServiceImpl.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<MatchLeague> getAllMatchsByLeague(Long leagueId) {
		List<MatchLeague> matchs = null;
		try {
			MatchLeagueDao dao = new MatchLeagueDao();
			matchs = dao.findAllByLeague(leagueId);
			logger.warning("League ID: " + leagueId + " :: Num. partidos: "
					+ matchs.size());
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}

		return matchs;
	}

	@Override
	public Date getDateNow() {
		return new Date();
	}

}
