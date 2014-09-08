/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.domain.MatchFriendly;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * @author adou
 *
 */
@RemoteServiceRelativePath("matchFriendlyService")
public interface MatchFriendlyService extends RemoteService {

	List<MatchFriendly> getAllFriendlyMatchsByTactic(Long tacticId);
	
	Date getDateNow();
	
	MatchFriendly dispatchMatch(Long tacticId);

}
