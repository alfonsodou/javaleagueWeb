/**
 * 
 */
package org.javahispano.javaleague.shared.service;

import org.javahispano.javaleague.server.locator.DaoServiceLocator;
import org.javahispano.javaleague.server.service.TacticUserDao;
import org.javahispano.javaleague.shared.proxy.TacticUserProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * @author adou
 *
 */
@Service(value = TacticUserDao.class, locator = DaoServiceLocator.class)
public interface TacticUserService extends RequestContext {
	Request<TacticUserProxy> fetch(Long id);
	Request<Void> save(TacticUserProxy tacticUserProxy);
	Request<Void> remove(Long id);
	Request<TacticUserProxy> findByUserId(Long userId);
}
