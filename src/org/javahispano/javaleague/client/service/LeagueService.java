/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.domain.AppUser;
import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author adou
 *
 */
@RemoteServiceRelativePath("leagueService")
public interface LeagueService extends RemoteService {
	List<League> getAllLeagues();
	League getDefaultLeague();
	League getLeague(Long id);
	League addUser(Long id, Long appUserId);
	League deleteUser(Long id, Long appUserId);

}
