/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views.uibinder;

import org.javahispano.javaleague.client.mvp.presenters.FrameWorkPresenter;
import org.javahispano.javaleague.client.mvp.presenters.FrameWorkPrivatePresenter;
import org.javahispano.javaleague.client.mvp.views.FrameWorkPrivateView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class FrameWorkPrivateViewImpl extends Composite implements FrameWorkPrivateView {

	private static FrameWorkPrivateImplUiBinder uiBinder = GWT
			.create(FrameWorkPrivateImplUiBinder.class);

	interface FrameWorkPrivateImplUiBinder extends
			UiBinder<Widget, FrameWorkPrivateViewImpl> {
	}


	private FrameWorkPrivatePresenter presenter;
	
	public FrameWorkPrivateViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(FrameWorkPrivatePresenter presenter) {
		this.presenter = presenter;
	}

}
