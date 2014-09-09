/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views.uibinder;

import org.javahispano.javaleague.client.mvp.presenters.FrameWorkPresenter;
import org.javahispano.javaleague.client.mvp.views.FrameWorkView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class FrameWorkViewImpl extends Composite implements FrameWorkView {

	private static FrameWorkViewImplUiBinder uiBinder = GWT
			.create(FrameWorkViewImplUiBinder.class);

	interface FrameWorkViewImplUiBinder extends
			UiBinder<Widget, FrameWorkViewImpl> {
	}

	private FrameWorkPresenter presenter;
	
	public FrameWorkViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(FrameWorkPresenter presenter) {
		this.presenter = presenter;
	}

}
