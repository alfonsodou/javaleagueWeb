/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.ListGroup;
import org.gwtbootstrap3.client.ui.ListGroupItem;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.Alignment;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.gwtbootstrap3.client.ui.html.Small;
import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.resources.messages.AppLeagueMessages;
import org.javahispano.javaleague.client.service.RPCCall;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.MatchLeague;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class AppLeague extends Composite {

	private static AppLeagueUiBinder uiBinder = GWT
			.create(AppLeagueUiBinder.class);
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private AppLeagueMessages appLeagueMessages = GWT
			.create(AppLeagueMessages.class);

	interface AppLeagueUiBinder extends UiBinder<Widget, AppLeague> {
	}

	private League league = null;
	private List<MatchLeague> matchs = null;
	private Date now;

	@UiField
	DescriptionData nameLeague;
	@UiField
	DescriptionData endSignIn;
	@UiField
	DescriptionData numberTeams;
	@UiField
	Paragraph listMatchs;

	public AppLeague() {
		initWidget(uiBinder.createAndBindUi(this));

		setUp();
	}

	private void setUp() {
		getDefaultLeague();
	}

	private void getDefaultLeague() {
		new RPCCall<League>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert("Error fetch league ...");
			}

			@Override
			public void onSuccess(League result) {
				league = result;
				nameLeague.setText(result.getName());
				endSignIn.setText(DateTimeFormat.getFormat(
						PredefinedFormat.DATE_TIME_MEDIUM).format(
						result.getEndSignIn()));
				if (result.getAppUsers() != null) {
					numberTeams.setText(Integer.toString(result.getAppUsers()
							.size()));
				} else {
					numberTeams.setText("0");
				}

				checkDate();
			}

			@Override
			protected void callService(AsyncCallback<League> cb) {
				clientFactory.getLeagueService().getDefaultLeague(cb);
			}

		}.retry(3);
	}

	private void getMatchs() {
		new RPCCall<List<MatchLeague>>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert("Error fetch matchs ...");
			}

			@Override
			public void onSuccess(List<MatchLeague> result) {
				matchs = result;

				showMatchs();
			}

			@Override
			protected void callService(AsyncCallback<List<MatchLeague>> cb) {
				clientFactory.getMatchLeagueService().getAllMatchsByLeague(
						league.getId(), cb);
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
				}
				
				getMatchs();
			}

			@Override
			protected void callService(AsyncCallback<Date> cb) {
				clientFactory.getMatchFriendlyService().getDateNow(cb);
			}

		}.retry(3);

	}

	private void showMatchs() {
		listMatchs.clear();
		ListGroup lg = new ListGroup();
		for (MatchLeague m : matchs) {
			ListGroupItem lgi = new ListGroupItem();

			Row row = new Row();

			row.add(addTeam(m.getLocalTeamId(), m.getNameLocal(),
					m.getNameLocalManager()));

			row.add(addResult(m.getLocalGoals(), m.getVisitingTeamGoals(),
					m.getLocalPossesion(), m.getState(), m.getId(),
					m.getVisualization()));

			row.add(addTeam(m.getVisitingTeamId(), m.getNameForeign(),
					m.getNameVisitingManager()));

			row.add(addLinks(m.getId(), m.getLocalTeamId(),
					m.getVisitingTeamId(), m.getState(), m.getVisualization()));
			lgi.add(row);
			lg.add(lgi);
		}
		listMatchs.add(lg);
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
		column.setSize(ColumnSize.MD_3);
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
			anchor.setText(appLeagueMessages.matchError());
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
					anchor.setText(appLeagueMessages.live());
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

				/*
				 * button.setText("Prueba"); button.addClickHandler(new
				 * ClickHandler() {
				 * 
				 * @Override public void onClick(ClickEvent event) { goTo(new
				 * ShowMatchPlace(Long.toString(id)));
				 * 
				 * }
				 * 
				 * });
				 */

				/*
				 * MyClickHandlerMatch myClickHandler = new MyClickHandlerMatch(
				 * id, eventBus); anchor.addClickHandler(myClickHandler);
				 */
			}
			break;
		}

		result.add(anchor);
		// result.add(button);

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
						AppLib.MINUTES_AFTER_LIVE_MATCH)))) {
			Paragraph p = new Paragraph();
			p.setAlignment(Alignment.CENTER);
			Anchor match = new Anchor();
			match.setIcon(IconType.DOWNLOAD);
			match.setHref(AppLib.baseURL + "/serveFriendly?id="
					+ Long.toString(id));
			p.add(match);

			/*
			 * if (tacticUser.getId().equals(localId)) { Anchor csv = new
			 * Anchor(); csv.setIcon(IconType.CALENDAR);
			 * csv.setHref(AppLib.baseURL + "/timeTacticMatch?id=" +
			 * Long.toString(id) + "&tactic=" + Long.toString(localId));
			 * 
			 * p.add(csv);
			 * 
			 * } else if (tacticUser.getId().equals(visitingId)) { Anchor csv =
			 * new Anchor(); csv.setIcon(IconType.CALENDAR);
			 * csv.setHref(AppLib.baseURL + "/timeTacticMatch?id=" +
			 * Long.toString(id) + "&tactic=" + Long.toString(visitingId));
			 * 
			 * p.add(csv); }
			 */

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

}
