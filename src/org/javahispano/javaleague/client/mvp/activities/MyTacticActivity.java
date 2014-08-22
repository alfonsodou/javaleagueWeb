/**
 * 
 */
package org.javahispano.javaleague.client.mvp.activities;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.MyTacticPlace;
import org.javahispano.javaleague.client.mvp.presenters.MyTacticPresenter;
import org.javahispano.javaleague.client.mvp.views.MyTacticView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * @author adou
 *
 */
public class MyTacticActivity extends AbstractActivity implements MyTacticPresenter {

	private ClientFactory clientFactory;
	private MyTacticView myTacticView;

	public MyTacticActivity(MyTacticPlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		myTacticView = clientFactory.getMyTacticView();
		panel.setWidget(myTacticView.asWidget());
		bind();		
	}

	public void bind() {
		myTacticView.setPresenter(this);
	}

    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }

}
