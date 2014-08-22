package org.javahispano.javaleague.client.mvp.views;

import org.javahispano.javaleague.client.mvp.presenters.RegisterPresenter;

import com.google.gwt.user.client.ui.IsWidget;

public interface RegisterView extends IsWidget {
	void setPresenter(RegisterPresenter presenter);
}
