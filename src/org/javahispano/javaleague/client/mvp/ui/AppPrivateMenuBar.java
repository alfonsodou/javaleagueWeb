/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import java.util.Date;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.MyTacticPlace;
import org.javahispano.javaleague.client.mvp.places.WelcomePlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class AppPrivateMenuBar extends Composite {

	private static AppPrivateMenuBarUiBinder uiBinder = GWT
			.create(AppPrivateMenuBarUiBinder.class);

	interface AppPrivateMenuBarUiBinder extends
			UiBinder<Widget, AppPrivateMenuBar> {
	}

	private ClientFactory clientFactory = GWT.create(ClientFactory.class);

	@UiField
	HasClickHandlers localeES;
	@UiField
	HasClickHandlers localeEN;
	@UiField
	AnchorButton locale;
	@UiField
	HasClickHandlers myTacticLink;
	@UiField
	HasClickHandlers logoutLink;
	@UiField
	AnchorButton userName;

	public AppPrivateMenuBar() {
		initWidget(uiBinder.createAndBindUi(this));
		setUp();
	}

	private void setUp() {
		String localeCookie = getLocaleCookie();
		if (localeCookie.equals("es"))
			locale.setText("Español (es)");
		else if (localeCookie.equals("en"))
			locale.setText("English (en)");

		localeES.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("AppPrivateMenuBar: select locale ES");
				locale.setText("Español (es)");
				setLocaleCookie("es");
			}
		});

		localeEN.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("AppPrivateMenuBar: select locale EN");
				locale.setText("English (en)");
				setLocaleCookie("en");
			}
		});
		
		userName.setText(clientFactory.getAppUser().getAppUserName());
		
		myTacticLink.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("AppPrivateMenuBar: select MyTactic");
				goTo(new MyTacticPlace());
			}
		});

		logoutLink.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("AppPrivateMenuBar: select Logout");
				// Falta borrar cookie para no recordar al usuario
				clientFactory.setAppUser(null);
				goTo(new WelcomePlace());
			}
		});

	}

	private void setLocaleCookie(String locale) {
		String cookie = getLocaleCookie();
		boolean control = false;

		if (cookie.equals(locale))
			control = true;
		else
			control = false;

		final String cookieName = LocaleInfo.getLocaleCookieName();
		if (cookieName != null) {
			Date expires = new Date();
			expires.setTime(expires.getTime() + (1000 * 60 * 60 * 24 * 21));
			Cookies.setCookie(cookieName, locale, expires);
		}

		if (!control) {
			com.google.gwt.user.client.Window.Location.reload();
		}
	}

	private String getLocaleCookie() {
		final String cookieName = LocaleInfo.getLocaleCookieName();
		return Cookies.getCookie(cookieName);
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}


}
