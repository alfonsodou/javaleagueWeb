package org.javahispano.javaleague.client.mvp;

import org.javahispano.javaleague.client.mvp.places.FrameWorkPlace;
import org.javahispano.javaleague.client.mvp.places.FrameWorkPrivatePlace;
import org.javahispano.javaleague.client.mvp.places.LeaguePlace;
import org.javahispano.javaleague.client.mvp.places.LeaguePrivatePlace;
import org.javahispano.javaleague.client.mvp.places.LoginPlace;
import org.javahispano.javaleague.client.mvp.places.MyTacticPlace;
import org.javahispano.javaleague.client.mvp.places.RegisterPlace;
import org.javahispano.javaleague.client.mvp.places.ShowMatchPlace;
import org.javahispano.javaleague.client.mvp.places.WelcomePlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
@WithTokenizers({ WelcomePlace.Tokenizer.class, RegisterPlace.Tokenizer.class,
		LoginPlace.Tokenizer.class, MyTacticPlace.Tokenizer.class,
		ShowMatchPlace.Tokenizer.class, FrameWorkPlace.Tokenizer.class,
		LeaguePlace.Tokenizer.class, LeaguePrivatePlace.Tokenizer.class,
		FrameWorkPrivatePlace.Tokenizer.class })
public interface AppPlacesHistoryMapper extends PlaceHistoryMapper {
}