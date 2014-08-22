/**
 * 
 */
package org.javahispano.javaleague.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * @author adou
 *
 */
public class LoginPlace extends Place {

	public LoginPlace() {
	}

	public static class Tokenizer implements PlaceTokenizer<LoginPlace> {

		public LoginPlace getPlace(String token) {
			return new LoginPlace();
		}

		public String getToken(LoginPlace place) {
			return null;
		}
	}

}
