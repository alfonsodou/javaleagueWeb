/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.domain.AppUser;
import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface LeagueServiceAsync {

	void getAllLeagues(AsyncCallback<List<League>> callback);

	void getDefaultLeague(AsyncCallback<League> callback);

	void getLeague(Long id, AsyncCallback<League> callback);

	void addUser(Long id, Long appUserId, AsyncCallback<League> callback);

	void deleteUser(Long id, Long appUserId, AsyncCallback<League> callback);

}
