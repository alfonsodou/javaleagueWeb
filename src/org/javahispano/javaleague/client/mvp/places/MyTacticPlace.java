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
public class MyTacticPlace extends Place {

	public MyTacticPlace() {
	}

	public static class Tokenizer implements PlaceTokenizer<MyTacticPlace> {

		public MyTacticPlace getPlace(String token) {
			return new MyTacticPlace();
		}

		public String getToken(MyTacticPlace place) {
			return null;
		}
	}

}
