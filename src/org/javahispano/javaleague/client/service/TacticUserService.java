/**
 * 
 */
package org.javahispano.javaleague.client.service;

import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author adou
 *
 */
@RemoteServiceRelativePath("tacticUserService")
public interface TacticUserService extends RemoteService {

	TacticUser fetchById(Long id);
	
	TacticUser fetchByUserId(Long id);
}
