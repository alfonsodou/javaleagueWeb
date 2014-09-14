/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Badge;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.Alignment;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Italic;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.gwtbootstrap3.client.ui.html.Small;
import org.gwtbootstrap3.client.ui.html.Span;
import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.places.ShowMatchPlace;
import org.javahispano.javaleague.client.resources.messages.AppMyTacticMessages;
import org.javahispano.javaleague.client.service.RPCCall;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.MatchFriendly;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.place.shared.Place;
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
	private List<MatchFriendly> listMatchFriendly = null;
	private Date now;

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
	Button updateWindowButton;
	@UiField
	Badge fileName;
	@UiField
	Paragraph listMatchs;

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

		playMatchButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				GWT.log("AppMyTactic: select play match: id: "
						+ tacticUser.getId());

				doPlayMatch();
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
						modal.setTitle(appMyTacticMessages
								.captionErrorValidateTactic());
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

		updateWindowButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				checkDate();
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
			playMatchButton.setEnabled(false);
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

	private void doPlayMatch() {
		new RPCCall<MatchFriendly>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert("Error Play Match ...");
			}

			@Override
			public void onSuccess(MatchFriendly result) {
				if (result == null) {
					waitForFriendlyMatch.setVisible(true);
				} else {
					listMatchFriendly.add(result);
					bubbleSort(listMatchFriendly);
					showMatchs();
				}
			}

			@Override
			protected void callService(AsyncCallback<MatchFriendly> cb) {
				clientFactory.getMatchFriendlyService().dispatchMatch(
						tacticUser.getId(), cb);
			}

		}.retry(3);
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
						playMatchButton.setEnabled(false);
					} else {
						fileName.setText(tacticUser.getFileNameJar() + " :: "
								+ tacticUser.getBytes() + " bytes");
					}
					if ((tacticUser.getValid())
							&& (tacticUser.getState() == AppLib.FRIENDLY_MATCH_SCHEDULED)) {
						waitForFriendlyMatch.setVisible(true);
					}

					checkDate();
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

	private void bubbleSort(List<MatchFriendly> array) {
		boolean swapped = true;
		int j = 0;
		MatchFriendly tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < array.size() - j; i++) {
				if (array.get(i).getVisualization()
						.compareTo(array.get(i + 1).getVisualization()) < 0) {
					tmp = array.get(i);
					array.set(i, array.get(i + 1));
					array.set(i + 1, tmp);
					swapped = true;
				}
			}
		}
	}

	private void checkMatchs() {
		new RPCCall<List<MatchFriendly>>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert("Error Fetch Match ...");
			}

			@Override
			public void onSuccess(List<MatchFriendly> result) {
				if ((result != null) && (result.size() > 0)) {
					listMatchFriendly = result;
					showMatchs();
				}
			}

			@Override
			protected void callService(AsyncCallback<List<MatchFriendly>> cb) {
				clientFactory.getMatchFriendlyService()
						.getAllFriendlyMatchsByTactic(tacticUser.getId(), cb);
			}

		}.retry(3);
	}

	private void checkDate() {

		new RPCCall<Date>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert("Error Fetch Date ...");
			}

			@Override
			public void onSuccess(Date result) {
				if (result != null) {
					now = result;

					checkMatchs();
				}
			}

			@Override
			protected void callService(AsyncCallback<Date> cb) {
				clientFactory.getMatchFriendlyService().getDateNow(cb);
			}

		}.retry(3);

	}

	private void showMatchs() {
		listMatchs.clear();
		for (MatchFriendly m : listMatchFriendly) {
			Row row = new Row();

			row.add(addType(m.getVisualization()));
			row.add(addTeam(m.getLocalTeamId(), m.getNameLocal(),
					m.getNameLocalManager()));

			row.add(addResult(m.getLocalGoals(), m.getVisitingTeamGoals(),
					m.getLocalPossesion(), m.getState(), m.getId(),
					m.getVisualization()));

			row.add(addTeam(m.getVisitingTeamId(), m.getNameForeign(),
					m.getNameVisitingManager()));

			row.add(addLinks(m.getId(), m.getLocalTeamId(),
					m.getVisitingTeamId(), m.getState(), m.getVisualization()));

			listMatchs.add(row);
		}
	}

	private Column addType(Date dateTimeMatch) {
		Column column = new Column();

		column.setSize(ColumnSize.MD_2);
		Paragraph p = new Paragraph();
		p.setAlignment(Alignment.CENTER);

		Paragraph pa = new Paragraph();
		Italic italics = new Italic();
		italics.setText(appMyTacticMessages.friendly());
		pa.add(italics);
		
		Small dateTime = new Small();
		dateTime.setText(DateTimeFormat.getFormat(
				PredefinedFormat.DATE_TIME_MEDIUM).format(dateTimeMatch));

		p.add(pa);
		p.add(dateTime);

		column.add(p);

		return column;
	}

	private Column addTeam(Long id, String name, String nameManager) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_3);
		Paragraph p = new Paragraph();
		p.setAlignment(Alignment.CENTER);
		Paragraph teamName = new Paragraph();
		Small managerName = new Small();

		teamName.setText(name);
		managerName.setText(nameManager);

		p.add(teamName);
		p.add(managerName);

		column.add(p);

		return column;
	}

	private Column addResult(int localGoals, int visitingTeamGoals,
			double localPossesion, int state, final Long id, Date d) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_2);
		Paragraph p = new Paragraph();
		p.setAlignment(Alignment.CENTER);
		Paragraph result = new Paragraph();
		Small possesion = new Small();
		DateTimeFormat date = DateTimeFormat
				.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);

		Anchor anchor = new Anchor();
		Button button = new Button();

		switch (state) {
		case AppLib.MATCH_ERROR:
			anchor.setText(appMyTacticMessages.matchError());
			anchor.setHref(AppLib.baseURL + "/serveError?id="
					+ Long.toString(id));
			break;
		case AppLib.MATCH_SCHEDULED:
			anchor.setText(date.format(d));
			break;
		case AppLib.MATCH_QUEUE:
			anchor.setText(date.format(d));
			break;
		case AppLib.MATCH_OK:
			if (now.before(d)) {
				anchor.setText(date.format(d));
			} else {
				if (now.after(d)
						&& now.before(addMinutesToDate(d,
								AppLib.MINUTES_AFTER_LIVE_MATCH))) {
					anchor.setText(appMyTacticMessages.live());
				} else {
					anchor.setText(localGoals + " - " + visitingTeamGoals);
					possesion.setText(round(localPossesion * 100d, 2) + " - "
							+ round((1d - localPossesion) * 100d, 2));
				}
				/*
				 * De momento el enlace descarga el partido Falta arreglar el
				 * visor para ver el partido en directo
				 */
				/*
				 * anchor.setHref(AppLib.baseURL + "/serve?id=" +
				 * Long.toString(id));
				 */

				anchor.setTarget("_blank");
				anchor.setHref(AppLib.baseURL + "/visorwebgl/play.html?"
						+ Long.toString(id));

				/*button.setText("Prueba");
				button.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						goTo(new ShowMatchPlace(Long.toString(id)));

					}

				});
				*/

				/*
				 * MyClickHandlerMatch myClickHandler = new MyClickHandlerMatch(
				 * id, eventBus); anchor.addClickHandler(myClickHandler);
				 */
			}
			break;
		}

		result.add(anchor);
		//result.add(button);

		p.add(result);
		p.add(possesion);

		column.add(p);

		return column;
	}

	private Column addLinks(Long id, Long localId, Long visitingId, int state,
			Date d) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_1);

		if ((state == AppLib.MATCH_OK)
				&& !(now.before(addMinutesToDate(d,
						-AppLib.MINUTES_BEFORE_LIVE_MATCH)))
				&& !(now.after(addMinutesToDate(d,
						-AppLib.MINUTES_BEFORE_LIVE_MATCH)) && now
						.before(addMinutesToDate(d,
								AppLib.MINUTES_AFTER_LIVE_MATCH)))) {
			Paragraph p = new Paragraph();
			p.setAlignment(Alignment.CENTER);
			Anchor match = new Anchor();
			match.setIcon(IconType.DOWNLOAD);
			match.setHref(AppLib.baseURL + "/serveFriendly?id="
					+ Long.toString(id));
			p.add(match);

			if (tacticUser.getId().equals(localId)) {
				Anchor csv = new Anchor();
				csv.setIcon(IconType.CALENDAR);
				csv.setHref(AppLib.baseURL + "/timeTacticMatch?id="
						+ Long.toString(id) + "&tactic="
						+ Long.toString(localId));

				p.add(csv);

			} else if (tacticUser.getId().equals(visitingId)) {
				Anchor csv = new Anchor();
				csv.setIcon(IconType.CALENDAR);
				csv.setHref(AppLib.baseURL + "/timeTacticMatch?id="
						+ Long.toString(id) + "&tactic="
						+ Long.toString(visitingId));

				p.add(csv);
			}

			column.add(p);
		}

		return column;
	}

	private double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	private Date addMinutesToDate(Date date, int minutes) {
		return new Date(date.getTime() + (minutes * 60 * 1000));
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
