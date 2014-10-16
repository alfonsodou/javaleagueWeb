/**
 * 
 */
package org.javahispano.javaleague.client.resources.messages;


import com.google.gwt.i18n.client.Messages;

/**
 * @author adou
 *
 */
public interface AppLeagueMessages extends Messages {
	@DefaultMessage("ERROR!")
	String matchError();
	@DefaultMessage("EN DIRECTO!")
	String live();
}
