/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.NavbarLink;
import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.service.RPCCall;
import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.core.client.GWT;
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

	public AppPrivateLeague() {
		initWidget(uiBinder.createAndBindUi(this));

		setUp();
	}

	private void setUp() {
		getDefaultLeague();
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
				nameLeague.setText(result.getName());
				endSignIn.setText(DateTimeFormat.getFormat(
						PredefinedFormat.DATE_TIME_MEDIUM).format(
						result.getEndSignIn()));
				if (result.getAppUsers() != null) {
					numberTeams.setText(Integer.toString(result.getAppUsers()
							.size()));
				} else {
					numberTeams.setText("0");
				}

			}

			@Override
			protected void callService(AsyncCallback<League> cb) {
				clientFactory.getLeagueService().getDefaultLeague(cb);
			}

		}.retry(3);
	}

}
