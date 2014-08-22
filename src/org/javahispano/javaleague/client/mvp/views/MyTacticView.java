/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views;

import org.javahispano.javaleague.client.mvp.presenters.MyTacticPresenter;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author adou
 *
 */
public interface MyTacticView extends IsWidget {
	void setPresenter(MyTacticPresenter presenter);

}
