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
public class LeaguePlace extends Place {

	public LeaguePlace() {
	}

	public static class Tokenizer implements PlaceTokenizer<LeaguePlace> {

		public LeaguePlace getPlace(String token) {
			return new LeaguePlace();
		}

		public String getToken(LeaguePlace place) {
			return null;
		}
	}

}
