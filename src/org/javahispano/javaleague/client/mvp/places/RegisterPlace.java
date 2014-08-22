package org.javahispano.javaleague.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class RegisterPlace extends Place {

	public RegisterPlace() {
	}

	public static class Tokenizer implements PlaceTokenizer<RegisterPlace> {

		public RegisterPlace getPlace(String token) {
			return new RegisterPlace();
		}

		public String getToken(RegisterPlace place) {
			return null;
		}
	}
}
