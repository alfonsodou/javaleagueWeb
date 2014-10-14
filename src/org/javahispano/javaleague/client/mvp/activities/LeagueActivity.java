/**
 * 
 */
package org.javahispano.javaleague.client.mvp.activities;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.LeaguePlace;
import org.javahispano.javaleague.client.mvp.presenters.LeaguePresenter;
import org.javahispano.javaleague.client.mvp.views.LeagueView;
import org.javahispano.javaleague.client.service.RPCCall;
import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * @author adou
 *
 */
public class LeagueActivity extends AbstractActivity implements LeaguePresenter {

	private ClientFactory clientFactory;
	private LeagueView leagueView;
	private League league;

	public LeagueActivity(LeaguePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		leagueView = clientFactory.getLeagueView();
		panel.setWidget(leagueView.asWidget());
		bind();
	}

	public void bind() {
		//getDefaultLeague();
		leagueView.setPresenter(this);
		/*leagueView.displayLeague(league.getName(),
				DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM)
						.format(league.getEndSignIn()), Integer.toString(league
						.getAppUsers().size()));
						*/
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
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
			}

			@Override
			protected void callService(AsyncCallback<League> cb) {
				clientFactory.getLeagueService().getDefaultLeague(cb);
			}

		}.retry(3);
	}

}
