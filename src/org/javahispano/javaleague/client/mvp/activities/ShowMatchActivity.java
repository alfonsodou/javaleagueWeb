package org.javahispano.javaleague.client.mvp.activities;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.ShowMatchPlace;
import org.javahispano.javaleague.client.mvp.presenters.ShowMatchPresenter;
import org.javahispano.javaleague.client.mvp.views.ShowMatchView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


public class ShowMatchActivity extends AbstractActivity implements ShowMatchPresenter {

	private ClientFactory clientFactory;
	private ShowMatchView showMatchView;

	public ShowMatchActivity(ShowMatchPlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		showMatchView = clientFactory.getShowMatchView();
		panel.setWidget(showMatchView.asWidget());
		bind();		
	}

	public void bind() {
		showMatchView.setPresenter(this);
	}

    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }

}
