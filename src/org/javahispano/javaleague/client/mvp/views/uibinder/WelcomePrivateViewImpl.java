/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views.uibinder;

import org.javahispano.javaleague.client.mvp.presenters.WelcomePrivatePresenter;
import org.javahispano.javaleague.client.mvp.views.WelcomePrivateView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class WelcomePrivateViewImpl extends Composite implements
		WelcomePrivateView {

	private static WelcomePrivateViewUiBinder uiBinder = GWT
			.create(WelcomePrivateViewUiBinder.class);

	interface WelcomePrivateViewUiBinder extends
			UiBinder<Widget, WelcomePrivateViewImpl> {
	}

	private WelcomePrivatePresenter presenter;

	public WelcomePrivateViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(WelcomePrivatePresenter presenter) {
		this.presenter = presenter;
	}

}
