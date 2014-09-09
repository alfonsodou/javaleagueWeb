/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views;

import org.javahispano.javaleague.client.mvp.presenters.ShowMatchPresenter;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author adou
 *
 */
public interface ShowMatchView extends IsWidget {
	void setPresenter(ShowMatchPresenter presenter);

}
