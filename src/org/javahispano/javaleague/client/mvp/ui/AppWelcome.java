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
public class AppWelcome extends Composite {

	private static AppWelcomeUiBinder uiBinder = GWT
			.create(AppWelcomeUiBinder.class);

	interface AppWelcomeUiBinder extends UiBinder<Widget, AppWelcome> {
	}

	public AppWelcome() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
