/**
 * 
 */
package org.javahispano.javaleague.client;

import org.javahispano.javaleague.client.mvp.AppPlacesHistoryMapper;
import org.javahispano.javaleague.client.mvp.views.FrameWorkPrivateView;
import org.javahispano.javaleague.client.mvp.views.FrameWorkView;
import org.javahispano.javaleague.client.mvp.views.LeaguePrivateView;
import org.javahispano.javaleague.client.mvp.views.LeagueView;
import org.javahispano.javaleague.client.mvp.views.LoginView;
import org.javahispano.javaleague.client.mvp.views.MyTacticView;
import org.javahispano.javaleague.client.mvp.views.RegisterView;
import org.javahispano.javaleague.client.mvp.views.ShowMatchView;
import org.javahispano.javaleague.client.mvp.views.WelcomePrivateView;
import org.javahispano.javaleague.client.mvp.views.WelcomeView;
import org.javahispano.javaleague.client.mvp.views.uibinder.FrameWorkPrivateViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.FrameWorkViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.LeaguePrivateViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.LeagueViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.LoginViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.MyTacticViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.RegisterViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.ShowMatchViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.WelcomePrivateViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.WelcomeViewImpl;
import org.javahispano.javaleague.client.service.AppUserService;
import org.javahispano.javaleague.client.service.AppUserServiceAsync;
import org.javahispano.javaleague.client.service.FrameWorkService;
import org.javahispano.javaleague.client.service.FrameWorkServiceAsync;
import org.javahispano.javaleague.client.service.LeagueService;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.MatchFriendlyService;
import org.javahispano.javaleague.client.service.MatchFriendlyServiceAsync;
import org.javahispano.javaleague.client.service.RPCCall;
import org.javahispano.javaleague.client.service.TacticUserService;
import org.javahispano.javaleague.client.service.TacticUserServiceAsync;
import org.javahispano.javaleague.shared.domain.AppUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * @author adou
 * 
 */
public class ClientFactoryImpl implements ClientFactory {
	private static EventBus eventBus = new SimpleEventBus();
	private static PlaceController placeController;

	private static WelcomeView welcomeView;
	private static RegisterView registerView;
	private static LoginView loginView;
	private static MyTacticView myTacticView;
	private static ShowMatchView showMatchView;
	private static FrameWorkView frameWorkView;
	private static LeagueView leagueView;
	private static LeaguePrivateView leaguePrivateView;
	private static FrameWorkPrivateView frameWorkPrivateView;
	private static WelcomePrivateView welcomePrivateView;

	private static AppUser appUser;

	private AppPlacesHistoryMapper historyMapper = GWT
			.create(AppPlacesHistoryMapper.class);

	private AppUserServiceAsync appUserService = GWT
			.create(AppUserService.class);
	private TacticUserServiceAsync tacticUserService = GWT
			.create(TacticUserService.class);
	private MatchFriendlyServiceAsync matchFriendlyService = GWT
			.create(MatchFriendlyService.class);
	private FrameWorkServiceAsync frameWorkService = GWT
			.create(FrameWorkService.class);
	private LeagueServiceAsync leagueService = GWT.create(LeagueService.class);

	public ClientFactoryImpl() {

	}

	@Override
	public EventBus getEventBus() {
		if (eventBus == null)
			eventBus = new SimpleEventBus();
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		if (placeController == null)
			placeController = new PlaceController(getEventBus());
		return placeController;
	}

	@Override
	public WelcomeView getWelcomeView() {
		if (welcomeView == null)
			welcomeView = new WelcomeViewImpl();
		return welcomeView;
	}

	@Override
	public RegisterView getRegisterView() {
		if (registerView == null)
			registerView = new RegisterViewImpl();
		return registerView;
	}

	@Override
	public AppPlacesHistoryMapper getHistoryMapper() {
		if (historyMapper == null)
			historyMapper = GWT.create(AppPlacesHistoryMapper.class);
		return historyMapper;
	}

	@Override
	public LoginView getLoginView() {
		if (loginView == null)
			loginView = new LoginViewImpl();
		return loginView;
	}

	@Override
	public MyTacticView getMyTacticView() {
		if (myTacticView == null)
			myTacticView = new MyTacticViewImpl();
		return myTacticView;
	}

	@Override
	public AppUser getAppUser() {
		if (ClientFactoryImpl.appUser == null) {
			new RPCCall<AppUser>() {

				@Override
				public void onFailure(Throwable caught) {
					GWT.log("ClientFactoryImpl: Error getAppUser");
				}

				@Override
				public void onSuccess(AppUser result) {
					ClientFactoryImpl.appUser = result;
				}

				@Override
				protected void callService(AsyncCallback<AppUser> cb) {
					appUserService.getLoggedInUser(cb);
				}

			}.retry(3);
		}

		return ClientFactoryImpl.appUser;
	}

	@Override
	public void setAppUser(AppUser appUser) {
		ClientFactoryImpl.appUser = appUser;

	}

	@Override
	public AppUserServiceAsync getAppUserService() {
		if (appUserService == null)
			appUserService = GWT.create(AppUserService.class);
		return appUserService;
	}

	@Override
	public TacticUserServiceAsync getTacticUserService() {
		if (tacticUserService == null)
			tacticUserService = GWT.create(TacticUserService.class);
		return tacticUserService;
	}

	@Override
	public void init() {
		appUser = null;
		welcomeView = null;
		registerView = null;
		myTacticView = null;
	}

	@Override
	public MatchFriendlyServiceAsync getMatchFriendlyService() {
		if (matchFriendlyService == null)
			matchFriendlyService = GWT.create(MatchFriendlyService.class);
		return matchFriendlyService;
	}

	@Override
	public ShowMatchView getShowMatchView() {
		if (showMatchView == null)
			showMatchView = new ShowMatchViewImpl();
		return showMatchView;
	}

	@Override
	public FrameWorkView getFrameWorkView() {
		if (frameWorkView == null)
			frameWorkView = new FrameWorkViewImpl();
		return frameWorkView;
	}

	@Override
	public FrameWorkServiceAsync getFrameWorkService() {
		if (frameWorkService == null)
			frameWorkService = GWT.create(FrameWorkService.class);
		return frameWorkService;
	}

	@Override
	public LeagueView getLeagueView() {
		if (leagueView == null)
			leagueView = new LeagueViewImpl();
		return leagueView;
	}

	@Override
	public LeagueServiceAsync getLeagueService() {
		if (leagueService == null)
			leagueService = GWT.create(LeagueService.class);
		return leagueService;
	}

	@Override
	public LeaguePrivateView getLeaguePrivateView() {
		if (leaguePrivateView == null)
			leaguePrivateView = new LeaguePrivateViewImpl();
		return leaguePrivateView;
	}

	@Override
	public FrameWorkPrivateView getFrameWorkPrivateView() {
		if (frameWorkPrivateView == null)
			frameWorkPrivateView = new FrameWorkPrivateViewImpl();
		return frameWorkPrivateView;
	}

	@Override
	public WelcomePrivateView getWelcomePrivateView() {
		if (welcomePrivateView == null)
			welcomePrivateView = new WelcomePrivateViewImpl();
		return welcomePrivateView;
	}

}
