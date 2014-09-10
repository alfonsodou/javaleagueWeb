/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.LeaguePlace;
import org.javahispano.javaleague.client.mvp.places.MyTacticPlace;
import org.javahispano.javaleague.client.mvp.places.WelcomePlace;
import org.javahispano.javaleague.client.service.RPCCall;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
	HasClickHandlers myTacticLink;
	@UiField
	HasClickHandlers logoutLink;
	@UiField
	HasClickHandlers leagueLink;
	@UiField
	AnchorButton userName;

	public AppPrivateMenuBar() {
		initWidget(uiBinder.createAndBindUi(this));
		setUp();
	}

	private void setUp() {

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

				logoutSession();

				clientFactory.init();

				goTo(new WelcomePlace());
			}
		});

		leagueLink.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				GWT.log("AppPrivateMenuBar: select League");
				goTo(new LeaguePlace());
			}
			
		});
	}

	private void logoutSession() {
		new RPCCall<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("AppPrivateMenuBar: Error Logout Session");
			}

			@Override
			public void onSuccess(Void result) {
				GWT.log("AppPrivateMenuBar: Logout Session");

			}

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				clientFactory.getAppUserService().logout(cb);
			}

		}.retry(3);
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
