/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.domain.MatchFriendly;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface MatchFriendlyServiceAsync {
	void getAllFriendlyMatchsByTactic(Long tacticId, AsyncCallback<List<MatchFriendly>> callback);

	void dispatchMatch(Long tacticId, AsyncCallback<MatchFriendly> callback);

	void getDateNow(AsyncCallback<Date> callback);

}
