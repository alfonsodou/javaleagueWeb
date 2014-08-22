/**
 * 
 */
package org.javahispano.javaleague.shared.service;

import org.javahispano.javaleague.server.locator.DaoServiceLocator;
import org.javahispano.javaleague.server.service.AppUserDao;
import org.javahispano.javaleague.shared.proxy.AppUserProxy;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * @author adou
 *
 */
@Service(value = AppUserDao.class, locator = DaoServiceLocator.class)
public interface AppUserService extends RequestContext {
	Request<AppUserProxy> fetch(Long id);
	Request<Void> save(AppUserProxy appUserProxy);
	Request<Void> remove(Long id);
	Request<AppUserProxy> findByEmail(String email);
	Request<Boolean> newUser(AppUserProxy appUserProxy, String teamName);
	Request<AppUserProxy> login(AppUserProxy appUserProxy);
}
