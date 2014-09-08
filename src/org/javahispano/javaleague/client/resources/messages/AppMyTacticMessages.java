/**
 * 
 */
package org.javahispano.javaleague.client.resources.messages;


import com.google.gwt.i18n.client.Messages;

/**
 * @author adou
 *
 */
public interface AppMyTacticMessages extends Messages {
	@DefaultMessage("Todas las clases de tu táctica deben estar incluidas en el package {0}")
	String packagePath(String path);
	@DefaultMessage(":: Sin táctica ::")
	String emptyUserTactic();
	@DefaultMessage("Error al ejecutar la táctica")
	String captionErrorValidateTactic();
	@DefaultMessage("OK")
	String okButton();	
	@DefaultMessage("Amistoso")
	String friendly();
	@DefaultMessage("ERROR!")
	String matchError();
	@DefaultMessage("EN DIRECTO!")
	String live();
}
