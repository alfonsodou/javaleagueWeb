/**
 * 
 */
package org.javahispano.javaleague.client.resources.messages;

import com.google.gwt.i18n.client.Messages;

/**
 * @author adou
 *
 */
public interface AppPrivateLeagueMessages extends Messages {
	@DefaultMessage("Gracias por apuntarte!")
	String signIn();
	@DefaultMessage("Esperamos que vuelvas a apuntarte")
	String signOut();

}
