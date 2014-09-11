package org.javahispano.javaleague.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class WelcomePrivatePlace extends Place {

	public WelcomePrivatePlace() {
	}

	public static class Tokenizer implements PlaceTokenizer<WelcomePrivatePlace> {

		public WelcomePrivatePlace getPlace(String token) {
			return new WelcomePrivatePlace();
		}

		public String getToken(WelcomePrivatePlace place) {
			return null;
		}
	}
}
