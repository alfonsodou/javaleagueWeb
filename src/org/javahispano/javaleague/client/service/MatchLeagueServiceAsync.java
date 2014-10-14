/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.domain.MatchLeague;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface MatchLeagueServiceAsync {

	void getDateNow(AsyncCallback<Date> callback);

	void getAllMatchsByLeague(Long leagueId,
			AsyncCallback<List<MatchLeague>> callback);

}
