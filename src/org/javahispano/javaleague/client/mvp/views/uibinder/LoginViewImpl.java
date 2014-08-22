/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views.uibinder;

import org.javahispano.javaleague.client.mvp.presenters.LoginPresenter;
import org.javahispano.javaleague.client.mvp.views.LoginView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class LoginViewImpl extends Composite implements LoginView {

	private static LoginViewImplUiBinder uiBinder = GWT
			.create(LoginViewImplUiBinder.class);
	
	private LoginPresenter presenter;

	interface LoginViewImplUiBinder extends UiBinder<Widget, LoginViewImpl> {
	}

	public LoginViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(LoginPresenter presenter) {
		this.presenter = presenter;
	}

}
