/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import java.util.Date;

import org.gwtbootstrap3.client.ui.Badge;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.gwtbootstrap3.client.ui.html.Span;
import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.resources.messages.AppMyTacticMessages;
import org.javahispano.javaleague.client.service.RPCCall;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

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
	private AppMyTacticMessages appMyTacticMessages = GWT
			.create(AppMyTacticMessages.class);

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
	@UiField
	Label waitForFriendlyMatch;
	@UiField
	Paragraph messagePackagePath;
	@UiField
	FormPanel formPanelTactic;
	@UiField
	Button updateTacticButton;
	@UiField
	Button playMatchButton;
	@UiField
	Badge fileName;

	public AppMyTactic() {
		initWidget(uiBinder.createAndBindUi(this));
		setUp();
	}

	private void setUp() {
		formPanelTactic.setMethod(FormPanel.METHOD_POST);
		formPanelTactic.setEncoding(FormPanel.ENCODING_MULTIPART);

		hideErrorLabel();

		waitForFriendlyMatch.setVisible(false);

		checkTactic();

		updateTacticButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("AppMyTactic: updating tactic. id: "
						+ tacticUser.getId());

				doUpdateTactic();
			}
		});

		formPanelTactic.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {

				if (!event.getResults().isEmpty()) {
					Document document = XMLParser.parse(event.getResults());
					Element tacticElement = document.getDocumentElement();
					XMLParser.removeWhitespace(tacticElement);

					int validate = Integer.parseInt(getElementTextValue(
							tacticElement, "error"));

					switch (validate) {
					case 0:
						showTactic(tacticElement);
						break;
					case 1:
						errorPackagePath.setVisible(true);
						break;
					case 2:
						errorInterfaceTactic.setVisible(true);
						break;
					case 3:
						final Modal modal = new Modal();
						modal.setTitle(appMyTacticMessages.captionErrorValidateTactic());
						modal.setClosable(true);

						final ModalBody modalBody = new ModalBody();
						modalBody.add(new Span(getElementTextValue(
								tacticElement, "stacktrace")));

						final ModalFooter modalFooter = new ModalFooter();
						modalFooter.add(new Button(appMyTacticMessages
								.okButton(), new ClickHandler() {
							@Override
							public void onClick(final ClickEvent event) {
								modal.hide();
							}
						}));
						modal.add(modalBody);
						modal.add(modalFooter);

						modal.show();
						break;
					}
				} else {
					fileName.setText(appMyTacticMessages.emptyUserTactic());
				}

				updateTacticButton.setEnabled(true);
			}
		});

	}

	private void showTactic(Element tacticElement) {
		DateTimeFormat fmt = DateTimeFormat
				.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
		Date now = new Date();

		teamName.setText(getElementTextValue(tacticElement, "teamname"));

		updatedTactic.setText(fmt.format(now));

		if (!getElementTextValue(tacticElement, "filename").equals(
				AppLib.NO_FILE)) {
			fileName.setText(getElementTextValue(tacticElement, "filename")
					+ " :: " + getElementTextValue(tacticElement, "bytes")
					+ " bytes");
			if (tacticUser.getState() != AppLib.FRIENDLY_MATCH_SCHEDULED) {
				playMatchButton.setEnabled(true);
			}
		} else {
			fileName.setText(appMyTacticMessages.emptyUserTactic());
		}

	}

	private void doUpdateTactic() {
		boolean error = false;

		hideErrorLabel();

		if (teamName.getValue().isEmpty()) {
			errorTeamName.setVisible(true);
			error = true;
		}

		if (!error) {
			updateTacticButton.setEnabled(false);

			tacticUser.setTeamName(teamName.getValue());
			formPanelTactic.submit();
		}
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
					messagePackagePath.setText(appMyTacticMessages
							.packagePath(AppLib.PATH_PACKAGE
									+ tacticUser.getId().toString()));
					if (tacticUser.getFileNameJar().equals(AppLib.NO_FILE)) {
						fileName.setText(appMyTacticMessages.emptyUserTactic());
					} else {
						fileName.setText(tacticUser.getFileNameJar());
					}
					if ((tacticUser.getValid())
							&& (tacticUser.getState() == AppLib.FRIENDLY_MATCH_SCHEDULED)) {
						waitForFriendlyMatch.setVisible(true);
					}
				}
			}

			@Override
			protected void callService(AsyncCallback<TacticUser> cb) {
				clientFactory.getTacticUserService().fetchById(
						clientFactory.getAppUser().getTacticUserId(), cb);
			}

		}.retry(3);
	}

	private String getElementTextValue(Element parent, String elementTag) {
		// If the xml is not coming from a known good source, this method would
		// have to include safety checks.
		return parent.getElementsByTagName(elementTag).item(0).getFirstChild()
				.getNodeValue();
	}
}
