/**
 * 
 */
package org.javahispano.javaleague.client.mvp.activities;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.FrameWorkPrivatePlace;
import org.javahispano.javaleague.client.mvp.presenters.FrameWorkPrivatePresenter;
import org.javahispano.javaleague.client.mvp.views.FrameWorkPrivateView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * @author adou
 *
 */
public class FrameWorkPrivateActivity extends AbstractActivity implements FrameWorkPrivatePresenter {

	private ClientFactory clientFactory;
	private FrameWorkPrivateView frameWorkPrivateView;

	public FrameWorkPrivateActivity(FrameWorkPrivatePlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		frameWorkPrivateView = clientFactory.getFrameWorkPrivateView();
		panel.setWidget(frameWorkPrivateView.asWidget());
		bind();		
	}

	public void bind() {
		frameWorkPrivateView.setPresenter(this);
	}

    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }

}
