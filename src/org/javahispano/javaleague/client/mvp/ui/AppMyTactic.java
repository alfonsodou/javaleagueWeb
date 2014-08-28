/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import org.gwtbootstrap3.client.ui.Label;
import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.shared.proxy.TacticUserProxy;
import org.javahispano.javaleague.shared.service.TacticUserService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

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

	private TacticUserProxy tacticUser = null;

	@UiField
	Label errorTeamName;
	@UiField
	Label errorPackagePath;
	@UiField
	Label errorInterfaceTactic;
	@UiField
	Label updatedTactic;
	@UiField
	TextBox teamName;

	public AppMyTactic() {
		initWidget(uiBinder.createAndBindUi(this));
		setUp();
	}

	private void setUp() {
		hideErrorLabel();

		checkTactic();

		if (tacticUser != null)
			showTactic();

	}

	private void hideErrorLabel() {
		errorTeamName.setVisible(false);
		errorPackagePath.setVisible(false);
		errorInterfaceTactic.setVisible(false);
	}

	private void checkTactic() {
		TacticUserService tacticUserService = clientFactory.getRequestFactory()
				.tacticUserService();

		tacticUserService.fetch(clientFactory.getAppUser().getTacticUserId())
				.fire(new Receiver<TacticUserProxy>() {
					@Override
					public void onSuccess(TacticUserProxy response) {
						if (response != null) {
							tacticUser = response;
						} else {
							Window.alert("Error al recuperar la táctica!");
						}
					}
				});

	}

	private void showTactic() {
		updatedTactic.setText(tacticUser.getUpdated().toString());
		teamName.setText(tacticUser.getTeamName());
	}

}
