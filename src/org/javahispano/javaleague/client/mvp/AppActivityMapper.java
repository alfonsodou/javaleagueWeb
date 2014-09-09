package org.javahispano.javaleague.client.mvp;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.activities.FrameWorkActivity;
import org.javahispano.javaleague.client.mvp.activities.LoginActivity;
import org.javahispano.javaleague.client.mvp.activities.MyTacticActivity;
import org.javahispano.javaleague.client.mvp.activities.RegisterActivity;
import org.javahispano.javaleague.client.mvp.activities.ShowMatchActivity;
import org.javahispano.javaleague.client.mvp.activities.WelcomeActivity;
import org.javahispano.javaleague.client.mvp.places.FrameWorkPlace;
import org.javahispano.javaleague.client.mvp.places.LoginPlace;
import org.javahispano.javaleague.client.mvp.places.MyTacticPlace;
import org.javahispano.javaleague.client.mvp.places.RegisterPlace;
import org.javahispano.javaleague.client.mvp.places.ShowMatchPlace;
import org.javahispano.javaleague.client.mvp.places.WelcomePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	public Activity getActivity(Place place) {
		if (place instanceof WelcomePlace)
			return new WelcomeActivity((WelcomePlace) place, clientFactory);
		else if (place instanceof RegisterPlace)
			return new RegisterActivity((RegisterPlace) place, clientFactory);
		else if (place instanceof LoginPlace)
			return new LoginActivity((LoginPlace) place, clientFactory);
		else if (place instanceof MyTacticPlace)
			return new MyTacticActivity((MyTacticPlace) place, clientFactory);
		else if (place instanceof ShowMatchPlace)
			return new ShowMatchActivity((ShowMatchPlace) place, clientFactory);
		else if (place instanceof FrameWorkPlace)
			return new FrameWorkActivity((FrameWorkPlace) place, clientFactory);
		else
			return null;
	}
}
