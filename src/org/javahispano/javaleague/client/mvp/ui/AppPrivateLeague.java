/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.NavbarLink;
import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.resources.messages.AppPrivateLeagueMessages;
import org.javahispano.javaleague.client.service.RPCCall;
import org.javahispano.javaleague.shared.domain.AppUser;
import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class AppPrivateLeague extends Composite {

	private static AppPrivateLeagueUiBinder uiBinder = GWT
			.create(AppPrivateLeagueUiBinder.class);
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private AppPrivateLeagueMessages appPrivateLeagueMessages = GWT
			.create(AppPrivateLeagueMessages.class);

	interface AppPrivateLeagueUiBinder extends
			UiBinder<Widget, AppPrivateLeague> {
	}

	@UiField
	DescriptionData nameLeague;
	@UiField
	DescriptionData endSignIn;
	@UiField
	DescriptionData numberTeams;
	@UiField
	NavbarLink signInNavbarLink;
	@UiField
	NavbarLink signOutNavbarLink;

	private League league;

	public AppPrivateLeague() {
		initWidget(uiBinder.createAndBindUi(this));

		setUp();
	}

	private void setUp() {
		getDefaultLeague();

		signInNavbarLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("AppPrivateLeague: select sigIn");
				signInLeague();
			}
		});

		signOutNavbarLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("AppPrivateLeague: select sigOut");
				signOutLeague();
			}
		});

	}

	private void signInLeague() {
		new RPCCall<League>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert("Error signin league ...");
			}

			@Override
			public void onSuccess(League result) {
				league = result;
				Window.alert(appPrivateLeagueMessages.signIn());
				showLeague();
			}

			@Override
			protected void callService(AsyncCallback<League> cb) {
				clientFactory.getLeagueService().addUser(league.getId(),
						clientFactory.getAppUser().getId(), cb);
			}

		}.retry(3);

	}

	private void signOutLeague() {
		new RPCCall<League>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert("Error signout league ...");
			}

			@Override
			public void onSuccess(League result) {
				league = result;
				Window.alert(appPrivateLeagueMessages.signOut());
				showLeague();
			}

			@Override
			protected void callService(AsyncCallback<League> cb) {
				clientFactory.getLeagueService().deleteUser(league.getId(),
						clientFactory.getAppUser().getId(), cb);
			}

		}.retry(3);

	}

	private void getDefaultLeague() {
		new RPCCall<League>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert("Error fetch league ...");
			}

			@Override
			public void onSuccess(League result) {
				league = result;
				showLeague();
			}

			@Override
			protected void callService(AsyncCallback<League> cb) {
				clientFactory.getLeagueService().getDefaultLeague(cb);
			}

		}.retry(3);
	}

	private boolean checkUser() {
		if (league.getAppUsers() != null) {
			for (Long appUserId : league.getAppUsers()) {
				if (appUserId.equals(clientFactory.getAppUser().getId()))
					return true;
			}
		}

		return false;
	}

	private void showLeague() {
		nameLeague.setText(league.getName());
		endSignIn.setText(DateTimeFormat.getFormat(
				PredefinedFormat.DATE_TIME_MEDIUM).format(
				league.getEndSignIn()));
		if (league.getAppUsers() != null) {
			numberTeams.setText(Integer.toString(league.getAppUsers()
					.size()));
		} else {
			numberTeams.setText("0");
		}

		if (checkUser()) {
			signOutNavbarLink.setVisible(true);
			signInNavbarLink.setVisible(false);
		} else {
			signOutNavbarLink.setVisible(false);
			signInNavbarLink.setVisible(true);
		}

	}
	
}
