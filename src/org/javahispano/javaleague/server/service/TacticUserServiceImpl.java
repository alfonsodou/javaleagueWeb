/**
 * 
 */
package org.javahispano.javaleague.server.service;

import org.javahispano.javaleague.client.service.TacticUserService;
import org.javahispano.javaleague.server.domain.TacticUserDao;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 *
 */
public class TacticUserServiceImpl extends RemoteServiceServlet implements
		TacticUserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TacticUserDao tacticUserDao = new TacticUserDao();

	@Override
	public TacticUser fetchById(Long id) {
		return tacticUserDao.fetch(id);
	}

	@Override
	public TacticUser fetchByUserId(Long id) {
		return tacticUserDao.findByUserId(id);
	}

}
