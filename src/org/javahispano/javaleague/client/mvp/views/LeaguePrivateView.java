/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views;

import org.javahispano.javaleague.client.mvp.presenters.LeaguePrivatePresenter;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author adou
 *
 */
public interface LeaguePrivateView extends IsWidget {
	void setPresenter(LeaguePrivatePresenter presenter);
}
