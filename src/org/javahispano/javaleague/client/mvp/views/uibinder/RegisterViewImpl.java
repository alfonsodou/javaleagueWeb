/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views.uibinder;

import org.javahispano.javaleague.client.mvp.presenters.RegisterPresenter;
import org.javahispano.javaleague.client.mvp.views.RegisterView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class RegisterViewImpl extends Composite implements RegisterView {

	private static RegisterViewImplUiBinder uiBinder = GWT
			.create(RegisterViewImplUiBinder.class);

	interface RegisterViewImplUiBinder extends
			UiBinder<Widget, RegisterViewImpl> {
	}
	
	private RegisterPresenter presenter;

	public RegisterViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(RegisterPresenter presenter) {
		this.presenter = presenter;
	}

}
