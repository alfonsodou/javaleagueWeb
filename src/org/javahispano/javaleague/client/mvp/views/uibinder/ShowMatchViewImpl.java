/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views.uibinder;

import org.javahispano.javaleague.client.mvp.presenters.ShowMatchPresenter;
import org.javahispano.javaleague.client.mvp.views.ShowMatchView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class ShowMatchViewImpl extends Composite implements ShowMatchView {

	private static ShowMatchViewImplUiBinder uiBinder = GWT
			.create(ShowMatchViewImplUiBinder.class);

	interface ShowMatchViewImplUiBinder extends
			UiBinder<Widget, ShowMatchViewImpl> {
	}

	private ShowMatchPresenter presenter;

	public ShowMatchViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(ShowMatchPresenter presenter) {
		this.presenter = presenter;
	}

}
