/**
 * 
 */
package org.javahispano.javaleague.server.service;

import java.util.logging.Logger;

import org.javahispano.javaleague.server.domain.AppUser;
import org.javahispano.javaleague.server.domain.TacticUser;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * @author adou
 *
 */
public class TacticUserDao extends ObjectifyDao<TacticUser> {

	private static Logger logger = Logger.getLogger(TacticUserDao.class.getName());

	public void save(TacticUser tacticUser) {
		this.put(tacticUser);
	}

	public void remove(Long id) {
		try {
			TacticUser tacticUser = this.get(id);
			this.delete(tacticUser);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public TacticUser fetch(Long id) {
		TacticUser tacticUser = null;
		try {
			tacticUser = this.get(id);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

		return tacticUser;
	}
	
	public TacticUser findByUserId(Long id) {
		TacticUser tacticUser = null;
		try {
			tacticUser = this.getByProperty2("userId", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tacticUser;
	}

}
