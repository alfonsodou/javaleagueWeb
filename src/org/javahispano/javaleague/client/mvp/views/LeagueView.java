/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views;

import org.javahispano.javaleague.client.mvp.presenters.LeaguePresenter;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author adou
 *
 */
public interface LeagueView extends IsWidget {
	void setPresenter(LeaguePresenter presenter);
}
