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
public class FrameWorkPlace extends Place {

	public FrameWorkPlace() {
	}

	public static class Tokenizer implements PlaceTokenizer<FrameWorkPlace> {

		public FrameWorkPlace getPlace(String token) {
			return new FrameWorkPlace();
		}

		public String getToken(FrameWorkPlace place) {
			return null;
		}
	}

}
