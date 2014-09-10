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
public class LeaguePrivatePlace extends Place {

	public LeaguePrivatePlace() {
	}

	public static class Tokenizer implements PlaceTokenizer<LeaguePrivatePlace> {

		public LeaguePrivatePlace getPlace(String token) {
			return new LeaguePrivatePlace();
		}

		public String getToken(LeaguePrivatePlace place) {
			return null;
		}
	}

}
