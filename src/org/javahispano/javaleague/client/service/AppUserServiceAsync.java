/**
 * 
 */
package org.javahispano.javaleague.client.service;

import org.javahispano.javaleague.shared.domain.AppUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface AppUserServiceAsync {

	void newUser(AppUser appUser, String teamName,
			AsyncCallback<Boolean> callback);

	void login(AppUser appUser, AsyncCallback<AppUser> callback);
	
	void logout(AsyncCallback<Void> callback);

	void getLoggedInUser(AsyncCallback<AppUser> callback);
}
