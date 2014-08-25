/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import org.javahispano.javaleague.client.ClientFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class AppMyTactic extends Composite {

	private static AppMyTacticUiBinder uiBinder = GWT
			.create(AppMyTacticUiBinder.class);

	interface AppMyTacticUiBinder extends UiBinder<Widget, AppMyTactic> {
	}
	
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);

	public AppMyTactic() {
		initWidget(uiBinder.createAndBindUi(this));
		setUp();
	}
	
	private void setUp() {
		
	}

}
