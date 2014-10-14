/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.domain.MatchFriendly;
import org.javahispano.javaleague.shared.domain.MatchLeague;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * @author adou
 *
 */
@RemoteServiceRelativePath("matchFriendlyService")
public interface MatchLeagueService extends RemoteService {

	List<MatchLeague> getAllMatchsByLeague(Long leagueId);
	
	Date getDateNow();

}
