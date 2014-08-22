/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views;

import org.javahispano.javaleague.client.mvp.presenters.LoginPresenter;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author adou
 *
 */
public interface LoginView extends IsWidget {
	void setPresenter(LoginPresenter presenter);
}
