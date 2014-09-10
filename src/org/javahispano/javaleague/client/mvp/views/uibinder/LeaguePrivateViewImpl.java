/**
 * 
 */
package org.javahispano.javaleague.client.mvp.views.uibinder;

import org.javahispano.javaleague.client.mvp.presenters.LeaguePrivatePresenter;
import org.javahispano.javaleague.client.mvp.views.LeaguePrivateView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class LeaguePrivateViewImpl extends Composite implements LeaguePrivateView {

	private static LeaguePrivateViewImplUiBinder uiBinder = GWT
			.create(LeaguePrivateViewImplUiBinder.class);

	interface LeaguePrivateViewImplUiBinder extends
			UiBinder<Widget, LeaguePrivateViewImpl> {
	}

	private LeaguePrivatePresenter presenter;
	
	public LeaguePrivateViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(LeaguePrivatePresenter presenter) {
		this.presenter = presenter;
	}

}
