/**
 * 
 */
package org.javahispano.javaleague.shared.proxy;

import java.util.Date;

import org.javahispano.javaleague.server.domain.TacticUser;
import org.javahispano.javaleague.server.locator.ObjectifyLocator;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * @author adou
 * 
 */
@ProxyFor(value = TacticUser.class, locator = ObjectifyLocator.class)
public interface TacticUserProxy extends EntityProxy {
	String getTeamName();
	Date getUpdated();
	String getFileNameJar();
	Long getBytes();
}
