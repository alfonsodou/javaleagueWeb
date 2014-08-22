/**
 * 
 */
package org.javahispano.javaleague.client.mvp.activities;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.LoginPlace;
import org.javahispano.javaleague.client.mvp.presenters.LoginPresenter;
import org.javahispano.javaleague.client.mvp.views.LoginView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * @author adou
 *
 */
public class LoginActivity extends AbstractActivity implements LoginPresenter {

	private ClientFactory clientFactory;
	private LoginView loginView;

	public LoginActivity(LoginPlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		loginView = clientFactory.getLoginView();
		panel.setWidget(loginView.asWidget());
		bind();		
	}

	public void bind() {
		loginView.setPresenter(this);
	}

    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }

}
