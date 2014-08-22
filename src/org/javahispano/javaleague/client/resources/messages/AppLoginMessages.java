/**
 * 
 */
package org.javahispano.javaleague.client.resources.messages;


import com.google.gwt.i18n.client.Messages;

/**
 * @author adou
 *
 */
public interface AppLoginMessages extends Messages {
	@DefaultMessage("Recordar contraseña")
	String captionLogin();
	@DefaultMessage("Contraseña generada. Recibir&aacute;s un correo con la nueva contraseña.")
	String okMessage();
	@DefaultMessage("OK")
	String okButton();
}
