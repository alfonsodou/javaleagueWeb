/**
 * 
 */
package org.javahispano.javaleague.client.mvp.activities;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.LeaguePlace;
import org.javahispano.javaleague.client.mvp.presenters.LeaguePresenter;
import org.javahispano.javaleague.client.mvp.views.LeagueView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * @author adou
 *
 */
public class LeagueActivity extends AbstractActivity implements LeaguePresenter {

	private ClientFactory clientFactory;
	private LeagueView leagueView;

	public LeagueActivity(LeaguePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		leagueView = clientFactory.getLeagueView();
		panel.setWidget(leagueView.asWidget());
		bind();
	}

	public void bind() {
		leagueView.setPresenter(this);
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
