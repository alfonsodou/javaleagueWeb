/**
 * 
 */
package org.javahispano.javaleague.client.service;

import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface TacticUserServiceAsync {

	/**
	 * 
	 * @see org.javahispano.javaleague.client.service.TacticUserService#fetchById(java.lang.Long)
	 */
	void fetchById(Long id, AsyncCallback<TacticUser> callback);

	/**
	 * 
	 * @see org.javahispano.javaleague.client.service.TacticUserService#fetchByUserId(java.lang.Long)
	 */
	void fetchByUserId(Long id, AsyncCallback<TacticUser> callback);

}
