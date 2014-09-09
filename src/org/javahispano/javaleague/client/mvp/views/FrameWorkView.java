/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views;

import org.javahispano.javaleague.client.mvp.presenters.FrameWorkPresenter;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author adou
 *
 */
public interface FrameWorkView extends IsWidget {
	void setPresenter(FrameWorkPresenter presenter);
}
