/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class AppFooter extends Composite {

	private static AppFooterUiBinder uiBinder = GWT
			.create(AppFooterUiBinder.class);

	interface AppFooterUiBinder extends UiBinder<Widget, AppFooter> {
	}

	public AppFooter() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
