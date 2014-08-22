package org.javahispano.javaleague.client.mvp.views.uibinder;

import org.javahispano.javaleague.client.mvp.presenters.WelcomePresenter;
import org.javahispano.javaleague.client.mvp.views.WelcomeView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WelcomeViewImpl extends Composite implements WelcomeView {

	interface WelcomeViewUiBinder extends UiBinder<Widget, WelcomeViewImpl> {
	}

	private static WelcomeViewUiBinder uiBinder = GWT
			.create(WelcomeViewUiBinder.class);

	private WelcomePresenter presenter;

	public WelcomeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(WelcomePresenter presenter) {
		this.presenter = presenter;
	}





}
