/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
import org.javahispano.javaleague.client.mvp.places.WelcomePlace;
import org.javahispano.javaleague.client.resources.messages.AppRegisterUserMessages;
import org.javahispano.javaleague.client.service.RPCCall;
import org.javahispano.javaleague.shared.domain.AppUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class AppRegisterUser extends Composite {

	private static AppRegisterUserUiBinder uiBinder = GWT
			.create(AppRegisterUserUiBinder.class);
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private AppRegisterUserMessages appRegisterUserMessages = GWT
			.create(AppRegisterUserMessages.class);

	interface AppRegisterUserUiBinder extends UiBinder<Widget, AppRegisterUser> {
	}

	@UiField
	FormPanel formPanelRegisterUser;
	@UiField
	Form formRegisterUser;
	@UiField
	TextBox userName;
	@UiField
	TextBox email;
	@UiField
	TextBox teamName;
	@UiField
	Input password;
	@UiField
	Input rePassword;
	@UiField
	Label errorUserName;
	@UiField
	Label errorTeamName;
	@UiField
	Label errorEmail;
	@UiField
	Label errorPassword;
	@UiField
	Label errorPasswordSize;
	@UiField
	Label errorRegisterEmail;
	@UiField
	Button registerButton;
	@UiField
	Button cancelButton;

	public AppRegisterUser() {
		initWidget(uiBinder.createAndBindUi(this));

		setUp();
	}

	private void setUp() {
		hideErrorLabel();

		formRegisterUser.reset();

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				userName.setFocus(true);
			}
		});

		registerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				checkForm();
			}
		});

		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hideErrorLabel();
				formRegisterUser.reset();
				goTo(new WelcomePlace());
			}
		});

	}

	void checkForm() {
		boolean error = false;

		hideErrorLabel();

		if (userName.getText().length() < 4) {
			errorUserName.setVisible(true);
			error = true;
		}

		if (!checkEmail(email.getText())) {
			errorEmail.setVisible(true);
			error = true;
		}

		if (teamName.getText().length() == 0) {
			errorTeamName.setVisible(true);
			error = true;
		}

		if (password.getText().length() < 4) {
			errorPasswordSize.setVisible(true);
			error = true;
		}

		if (!password.getText().equals(rePassword.getText())) {
			errorPassword.setVisible(true);
			error = true;
		}

		if (!error) {
			MessageDigest crypt = null;

			try {
				crypt = java.security.MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				Window.alert("MD5 not supported");
				return;
			}

			byte[] digested = crypt.digest(password.getValue().getBytes());

			String crypt_password = new String();

			// Converts bytes to string
			for (byte b : digested)
				crypt_password += Integer.toHexString(0xFF & b);

			final AppUser appUser = new AppUser();
			appUser.setAppUserName(userName.getText());
			appUser.setEmail(email.getValue());
			appUser.setPassword(crypt_password);

			registerButton.setEnabled(false);

			new RPCCall<Boolean>() {

				@Override
				public void onFailure(Throwable caught) {
					GWT.log(caught.getMessage());
					Window.alert("Error Register User ...");
					registerButton.setEnabled(true);
				}

				@Override
				public void onSuccess(Boolean result) {
					if (result == Boolean.TRUE) {
						final Modal modal = new Modal();
						modal.setTitle(appRegisterUserMessages
								.captionAppRegisterUser());
						modal.setClosable(true);

						final ModalBody modalBody = new ModalBody();
						modalBody.add(new Span(appRegisterUserMessages
								.okMessage()));

						final ModalFooter modalFooter = new ModalFooter();
						modalFooter.add(new Button(
								appRegisterUserMessages.okButton(),
								new ClickHandler() {
									@Override
									public void onClick(
											final ClickEvent event) {
										modal.hide();
									}
								}));
						modal.add(modalBody);
						modal.add(modalFooter);

						modal.show();

						formRegisterUser.reset();

						goTo(new WelcomePlace());
					} else {
						errorRegisterEmail.setVisible(true);
					}
					registerButton.setEnabled(true);
				}

				@Override
				protected void callService(AsyncCallback<Boolean> cb) {
					clientFactory.getAppUserService().newUser(appUser,
							teamName.getText(), cb);
				}

			}.retry(3);

		}
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
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

	private void hideErrorLabel() {
		errorUserName.setVisible(false);
		errorTeamName.setVisible(false);
		errorEmail.setVisible(false);
		errorPassword.setVisible(false);
		errorPasswordSize.setVisible(false);
		errorRegisterEmail.setVisible(false);
	}

}
