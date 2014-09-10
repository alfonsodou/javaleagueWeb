/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.domain.League;

/**
 * @author adou
 * 
 */
public class LeagueDao {
	public LeagueDao() {
		super();
	}

	public League save(League league) {
		league.setUpdated(new Date());
		ofy().save().entity(league).now();
		return league;
	}

	public League findById(Long id) {
		return ofy().load().type(League.class).id(id).now();
	}

	public League findDefaultLeague() {
		League league = ofy().load().type(League.class)
				.filter("defaultLeague", Boolean.TRUE).first().now();

		return league;
	}

	public List<League> findAllLeagues() {
		List<League> leagues = ofy().load().type(League.class)
				.order("-updated").list();

		return leagues;
	}
}
