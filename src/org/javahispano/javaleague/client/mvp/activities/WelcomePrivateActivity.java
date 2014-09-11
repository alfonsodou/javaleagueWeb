package org.javahispano.javaleague.client.mvp.activities;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.WelcomePrivatePlace;
import org.javahispano.javaleague.client.mvp.presenters.WelcomePrivatePresenter;
import org.javahispano.javaleague.client.mvp.views.WelcomePrivateView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class WelcomePrivateActivity extends AbstractActivity implements WelcomePrivatePresenter {

	private ClientFactory clientFactory;
	private WelcomePrivateView welcomePrivateView;

	public WelcomePrivateActivity(WelcomePrivatePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		welcomePrivateView = clientFactory.getWelcomePrivateView();
		panel.setWidget(welcomePrivateView.asWidget());
		bind();		
	}

	public void bind() {
		welcomePrivateView.setPresenter(this);
	}

    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }

}
