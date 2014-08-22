/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views.uibinder;

import org.javahispano.javaleague.client.mvp.presenters.MyTacticPresenter;
import org.javahispano.javaleague.client.mvp.views.MyTacticView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class MyTacticViewImpl extends Composite implements MyTacticView {

	private static MyTacticViewImplUiBinder uiBinder = GWT
			.create(MyTacticViewImplUiBinder.class);

	private MyTacticPresenter presenter;

	interface MyTacticViewImplUiBinder extends
			UiBinder<Widget, MyTacticViewImpl> {
	}

	public MyTacticViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(MyTacticPresenter presenter) {
		this.presenter = presenter;

	}

}
