package org.javahispano.javaleague.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ShowMatchPlace extends Place {
	private Long matchId;

	public ShowMatchPlace(String matchId) {
		this.matchId = Long.valueOf(matchId);
	}

	/**
	 * @return the matchId
	 */
	public Long getMatchId() {
		return matchId;
	}

	/**
	 * @param matchId the matchId to set
	 */
	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public static class Tokenizer implements PlaceTokenizer<ShowMatchPlace> {

		public ShowMatchPlace getPlace(String token) {
			return new ShowMatchPlace(token);
		}

		public String getToken(ShowMatchPlace place) {
			return place.getMatchId().toString();
		}
	}
}
