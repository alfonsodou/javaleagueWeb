package org.javahispano.javaleague.client.mvp.views;

import org.javahispano.javaleague.client.mvp.presenters.WelcomePresenter;

import com.google.gwt.user.client.ui.IsWidget;

public interface WelcomeView extends IsWidget {
	void setPresenter(WelcomePresenter presenter);
}
