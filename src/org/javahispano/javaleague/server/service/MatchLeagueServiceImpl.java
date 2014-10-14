/**
 * 
 */
package org.javahispano.javaleague.server.service;

import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.client.service.MatchLeagueService;
import org.javahispano.javaleague.shared.domain.MatchLeague;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 *
 */
public class MatchLeagueServiceImpl extends RemoteServiceServlet implements
		MatchLeagueService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<MatchLeague> getAllMatchsByLeague(Long leagueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDateNow() {
		return new Date();
	}

}
