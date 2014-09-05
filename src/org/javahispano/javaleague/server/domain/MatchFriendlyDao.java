/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.domain.FrameWork;
import org.javahispano.javaleague.shared.domain.MatchFriendly;

/**
 * @author adou
 * 
 */
public class MatchFriendlyDao {
	public MatchFriendlyDao() {
		super();
	}

	public MatchFriendly save(MatchFriendly matchFriendly) {
		matchFriendly.setUpdated(new Date());
		ofy().save().entity(matchFriendly).now();
		return matchFriendly;
	}

	public MatchFriendly findById(Long id) {
		return ofy().load().type(MatchFriendly.class).id(id).now();
	}

	public List<MatchFriendly> findAllByTactic(Long tacticId) {
		List<MatchFriendly> matchFriendlys = ofy().load()
				.type(MatchFriendly.class).filter("localTeamId", tacticId)
				.order("execution").list();

		matchFriendlys.addAll(ofy().load().type(MatchFriendly.class)
				.filter("visitingTeamId", tacticId).order("execution").list());

		return matchFriendlys;
	}
}
