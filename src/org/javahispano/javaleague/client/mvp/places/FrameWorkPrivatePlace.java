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
public class FrameWorkPrivatePlace extends Place {

	public FrameWorkPrivatePlace() {
	}

	public static class Tokenizer implements PlaceTokenizer<FrameWorkPrivatePlace> {

		public FrameWorkPrivatePlace getPlace(String token) {
			return new FrameWorkPrivatePlace();
		}

		public String getToken(FrameWorkPrivatePlace place) {
			return null;
		}
	}

}
