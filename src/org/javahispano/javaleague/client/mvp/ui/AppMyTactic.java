/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import org.gwtbootstrap3.client.ui.Label;
import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.service.RPCCall;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
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

	private TacticUser tacticUser = null;

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
		new RPCCall<TacticUser>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert("Error Fetch Tactic ...");
			}

			@Override
			public void onSuccess(TacticUser result) {
				tacticUser = result;
				if (result != null) {
					updatedTactic.setText(DateTimeFormat.getFormat(
							PredefinedFormat.DATE_TIME_MEDIUM).format(
							tacticUser.getUpdated()));
					teamName.setText(tacticUser.getTeamName());
				}
			}

			@Override
			protected void callService(AsyncCallback<TacticUser> cb) {
				clientFactory.getTacticUserService().fetchById(
						clientFactory.getAppUser().getTacticUserId(), cb);
			}

		}.retry(3);
	}

	private void showTactic() {
		updatedTactic.setText(tacticUser.getUpdated().toString());
		teamName.setText(tacticUser.getTeamName());
	}

}
