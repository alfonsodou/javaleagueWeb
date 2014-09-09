/**
 * 
 */
package org.javahispano.javaleague.client.mvp.activities;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.FrameWorkPlace;
import org.javahispano.javaleague.client.mvp.presenters.FrameWorkPresenter;
import org.javahispano.javaleague.client.mvp.views.FrameWorkView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * @author adou
 *
 */
public class FrameWorkActivity extends AbstractActivity implements FrameWorkPresenter {

	private ClientFactory clientFactory;
	private FrameWorkView frameWorkView;

	public FrameWorkActivity(FrameWorkPlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		frameWorkView = clientFactory.getFrameWorkView();
		panel.setWidget(frameWorkView.asWidget());
		bind();		
	}

	public void bind() {
		frameWorkView.setPresenter(this);
	}

    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }

}
