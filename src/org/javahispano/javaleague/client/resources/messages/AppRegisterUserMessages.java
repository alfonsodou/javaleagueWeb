/**
 * 
 */
package org.javahispano.javaleague.client.resources.messages;


import com.google.gwt.i18n.client.Messages;

/**
 * @author adou
 *
 */
public interface AppRegisterUserMessages extends Messages {
	@DefaultMessage("Registro Usuario")
	String captionAppRegisterUser();
	@DefaultMessage("Gracias por registrarte. Recibir&aacute;s un correo con las instrucciones a seguir para completar tu registro.")
	String okMessage();
	@DefaultMessage("OK")
	String okButton();
}
