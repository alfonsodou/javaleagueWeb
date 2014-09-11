package org.javahispano.javaleague.client.mvp.views;

import org.javahispano.javaleague.client.mvp.presenters.WelcomePrivatePresenter;

import com.google.gwt.user.client.ui.IsWidget;

public interface WelcomePrivateView extends IsWidget {
	void setPresenter(WelcomePrivatePresenter presenter);
}
