/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.html.Span;
import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.MyTacticPlace;
import org.javahispano.javaleague.client.mvp.places.RegisterPlace;
import org.javahispano.javaleague.client.mvp.places.WelcomePlace;
import org.javahispano.javaleague.client.resources.messages.AppLoginMessages;
import org.javahispano.javaleague.shared.proxy.AppUserProxy;
import org.javahispano.javaleague.shared.service.AppUserService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * @author adou
 *
 */
public class AppLogin extends Composite {

	private static AppLoginUiBinder uiBinder = GWT
			.create(AppLoginUiBinder.class);
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private AppLoginMessages appLoginMessages = GWT
			.create(AppLoginMessages.class);

	interface AppLoginUiBinder extends UiBinder<Widget, AppLogin> {
	}

	@UiField
	Button registerUserButton;
	@UiField
	Button cancelButton;
	@UiField
	Button loginButton;
	@UiField
	Form formLoginUser;
	@UiField
	TextBox emailTextBox;
	@UiField
	Input passwordTextBox;
	@UiField
	AnchorButton rememberPassword;
	@UiField
	Label errorLogin;

	public AppLogin() {
		initWidget(uiBinder.createAndBindUi(this));

		setUp();
	}

	private void setUp() {
		hideErrorLabel();
		
		formLoginUser.reset();
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				emailTextBox.setFocus(true);
			}
		});

		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hideErrorLabel();
				formLoginUser.reset();
				goTo(new WelcomePlace());
			}
		});

		registerUserButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hideErrorLabel();
				formLoginUser.reset();
				goTo(new RegisterPlace());
			}
		});

		rememberPassword.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final Modal modal = new Modal();
				modal.setTitle(appLoginMessages.captionLogin());
				modal.setClosable(true);

				final ModalBody modalBody = new ModalBody();
				modalBody.add(new Span(appLoginMessages.okMessage()));

				final ModalFooter modalFooter = new ModalFooter();
				modalFooter.add(new Button(appLoginMessages.okButton(),
						new ClickHandler() {
							@Override
							public void onClick(final ClickEvent event) {
								modal.hide();
							}
						}));
				modal.add(modalBody);
				modal.add(modalFooter);

				modal.show();

				formLoginUser.reset();
			}
		});

		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				checkForm();
			}
		});
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	public void checkForm() {
		boolean error = false;
		
		hideErrorLabel();
		
		if ((emailTextBox.getText().length() <= 4)
				|| (passwordTextBox.getText().length() <= 4)) {
			errorLogin.setVisible(true);
			error = true;
		}
		
		if (!error) {
			loginButton.setEnabled(false);
			
			MessageDigest crypt = null;

			try {
				crypt = java.security.MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				Window.alert("MD5 not supported");
				return;
			}

			byte[] digested = crypt.digest(passwordTextBox.getValue().getBytes());

			String crypt_password = new String();

			// Converts bytes to string
			for (byte b : digested)
				crypt_password += Integer.toHexString(0xFF & b);
			AppUserService appUserService = clientFactory.getRequestFactory()
					.appUserService();
			AppUserProxy appUser = appUserService.create(AppUserProxy.class);
			appUser.setEmail(emailTextBox.getValue());
			appUser.setPassword(crypt_password);
			
			appUserService.login(appUser).fire(new Receiver<AppUserProxy>() {
				@Override
				public void onSuccess(AppUserProxy response) {
					if (response != null) {
						clientFactory.setAppUser(response);
						formLoginUser.reset();

						goTo(new MyTacticPlace());
					} else {
						errorLogin.setVisible(true);
					}
					loginButton.setEnabled(true);
				}
			});
		}
	}
	
	private void hideErrorLabel() {
		errorLogin.setVisible(false);
	}

	/**
	 * minimum email l@n.co
	 * */
	public static boolean checkEmail(final String emlId) {
		// ex:ak@bv.gh
		if (emlId == null) {
			return false;
		}
		final int lngth = emlId.length();
		if (lngth < 6) {
			return false;
		}
		final int locationAt = emlId.indexOf('@');
		if (locationAt < 1) {
			return false;
		}
		final int postLastPeriod = emlId.lastIndexOf('.');
		if (postLastPeriod < 0) {
			return false;
		}
		if (lngth - postLastPeriod < 3) {
			return false;
		}
		if (postLastPeriod - locationAt < 1) {
			return false;
		}
		return true;
	}

}
