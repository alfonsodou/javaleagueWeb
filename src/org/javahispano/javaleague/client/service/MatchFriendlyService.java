/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.domain.MatchFriendly;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.RemoteService;


/**
 * @author adou
 *
 */
@RemoteServiceRelativePath("matchFriendlyService")
public interface MatchFriendlyService extends RemoteService {

	List<MatchFriendly> getAllFriendlyMatchsByTactic(Long tacticId);
	
	MatchFriendly dispatchMatch(Long tacticId);

}
