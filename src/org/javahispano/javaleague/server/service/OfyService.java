/**
 * 
 */
package org.javahispano.javaleague.server.service;


import org.javahispano.javaleague.server.domain.AppUser;
import org.javahispano.javaleague.server.domain.TacticUser;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * @author adou
 * 
 */
public class OfyService {
	static {
		factory().register(AppUser.class);
		factory().register(TacticUser.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}
}
