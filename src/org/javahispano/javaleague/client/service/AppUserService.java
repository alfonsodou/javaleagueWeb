/**
 * 
 */
package org.javahispano.javaleague.client.service;

import org.javahispano.javaleague.shared.domain.AppUser;
import org.javahispano.javaleague.shared.exception.NotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author adou
 *
 */
@RemoteServiceRelativePath("appUserService")
public interface AppUserService extends RemoteService {

	AppUser login(AppUser appUser);

	Boolean newUser(AppUser appUser, String teamName);

	void logout() throws NotLoggedInException;

	AppUser getLoggedInUser();

}
