/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views;

import org.javahispano.javaleague.client.mvp.presenters.FrameWorkPrivatePresenter;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author adou
 *
 */
public interface FrameWorkPrivateView extends IsWidget {
	void setPresenter(FrameWorkPrivatePresenter presenter);
}
