package org.javahispano.javaleague.client.mvp.activities;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.RegisterPlace;
import org.javahispano.javaleague.client.mvp.presenters.RegisterPresenter;
import org.javahispano.javaleague.client.mvp.views.RegisterView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


public class RegisterActivity extends AbstractActivity implements RegisterPresenter {

	private ClientFactory clientFactory;
	private RegisterView registerView;

	public RegisterActivity(RegisterPlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		registerView = clientFactory.getRegisterView();
		panel.setWidget(registerView.asWidget());
		bind();		
	}

	public void bind() {
		registerView.setPresenter(this);
	}

    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }

}
