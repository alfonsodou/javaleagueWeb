/**
 * 
 */
package org.javahispano.javaleague.client.mvp.activities;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.LeaguePrivatePlace;
import org.javahispano.javaleague.client.mvp.presenters.LeaguePrivatePresenter;
import org.javahispano.javaleague.client.mvp.views.LeaguePrivateView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * @author adou
 *
 */
public class LeaguePrivateActivity extends AbstractActivity implements LeaguePrivatePresenter {

	private ClientFactory clientFactory;
	private LeaguePrivateView leaguePrivateView;

	public LeaguePrivateActivity(LeaguePrivatePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		leaguePrivateView = clientFactory.getLeaguePrivateView();
		panel.setWidget(leaguePrivateView.asWidget());
		bind();
	}

	public void bind() {
		leaguePrivateView.setPresenter(this);
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
