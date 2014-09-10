/**
 * 
 */
package org.javahispano.javaleague.client;

import org.javahispano.javaleague.client.mvp.AppPlacesHistoryMapper;
import org.javahispano.javaleague.client.mvp.views.FrameWorkView;
import org.javahispano.javaleague.client.mvp.views.LeagueView;
import org.javahispano.javaleague.client.mvp.views.LoginView;
import org.javahispano.javaleague.client.mvp.views.MyTacticView;
import org.javahispano.javaleague.client.mvp.views.RegisterView;
import org.javahispano.javaleague.client.mvp.views.ShowMatchView;
import org.javahispano.javaleague.client.mvp.views.WelcomeView;
import org.javahispano.javaleague.client.service.AppUserServiceAsync;
import org.javahispano.javaleague.client.service.FrameWorkServiceAsync;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.MatchFriendlyServiceAsync;
import org.javahispano.javaleague.client.service.TacticUserServiceAsync;
import org.javahispano.javaleague.shared.domain.AppUser;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author adou
 * 
 */
public interface ClientFactory {
	public EventBus getEventBus();

	public PlaceController getPlaceController();

	public AppPlacesHistoryMapper getHistoryMapper();
	
	public WelcomeView getWelcomeView();

	public RegisterView getRegisterView();
	
	public LoginView getLoginView();
	
	public MyTacticView getMyTacticView();
	
	public ShowMatchView getShowMatchView();
	
	public FrameWorkView getFrameWorkView();
	
	public LeagueView getLeagueView();
	
	public AppUser getAppUser();
	
	public void setAppUser(AppUser appUser);
	
	public AppUserServiceAsync getAppUserService();
	
	public TacticUserServiceAsync getTacticUserService();
	
	public MatchFriendlyServiceAsync getMatchFriendlyService();
	
	public FrameWorkServiceAsync getFrameWorkService();
	
	public LeagueServiceAsync getLeagueService();
	
	public void init();
}
