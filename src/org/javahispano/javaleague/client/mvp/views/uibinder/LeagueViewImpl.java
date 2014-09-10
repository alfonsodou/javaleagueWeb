/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views.uibinder;

import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.mvp.presenters.LeaguePresenter;
import org.javahispano.javaleague.client.mvp.ui.AppPrivateMenuBar;
import org.javahispano.javaleague.client.mvp.views.LeagueView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class LeagueViewImpl extends Composite implements LeagueView {

	private static LeagueViewImplUiBinder uiBinder = GWT
			.create(LeagueViewImplUiBinder.class);

	interface LeagueViewImplUiBinder extends UiBinder<Widget, LeagueViewImpl> {
	}

	private ClientFactory clientFactory = GWT.create(ClientFactory.class);

	private LeaguePresenter presenter;
	
	@UiField
	SimplePanel headerPanel;

	public LeagueViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		setUp();
	}

	private void setUp() {
		if (clientFactory.getAppUser() != null) {
			headerPanel.clear();
			headerPanel.add(new AppPrivateMenuBar());
		}
	}

	@Override
	public void setPresenter(LeaguePresenter presenter) {
		this.presenter = presenter;
	}

}
