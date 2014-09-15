/**
 * 
 */
package org.javahispano.javaleague.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.LeagueService;
import org.javahispano.javaleague.server.domain.LeagueDao;
import org.javahispano.javaleague.server.utils.Utils;
import org.javahispano.javaleague.shared.domain.AppUser;
import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 *
 */
public class LeagueServiceImpl extends RemoteServiceServlet implements LeagueService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(LeagueServiceImpl.class
			.getName());

	private LeagueDao leagueDao = new LeagueDao();

	@Override
	public List<League> getAllLeagues() {
		List<League> leagues = new ArrayList<League>();
		try {
			leagues = leagueDao.findAllLeagues();
			return leagues;
		} catch (Exception e) {
			Utils.stackTraceToString(e);
		}
		return null;
	}

	@Override
	public League getDefaultLeague() {
		League league;
		try {
			league = leagueDao.findDefaultLeague();
			return league;
		} catch (Exception e) {
			Utils.stackTraceToString(e);
		}
		return null;
	}

	@Override
	public League getLeague(Long id) {
		League league;
		try {
			league = leagueDao.findById(id);
			return league;
		} catch (Exception e) {
			Utils.stackTraceToString(e);
		}
		return null;
	}

	@Override
	public League addUser(Long id, Long appUserId) {
		try {
			League league = leagueDao.findById(id);
			league.getAppUsers().add(appUserId);
			league = leagueDao.save(league);
			
			return league;
		} catch (Exception e) {
			Utils.stackTraceToString(e);
		}
		
		return null;
	}

	@Override
	public League deleteUser(Long id, Long appUserId) {
		try {
			League league = leagueDao.findById(id);
			league.getAppUsers().remove(appUserId);
			league = leagueDao.save(league);
			
			return league;
		} catch (Exception e) {
			Utils.stackTraceToString(e);
		}
		
		return null;
	}

}
