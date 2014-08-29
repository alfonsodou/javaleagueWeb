/**
 * 
 */
package org.javahispano.javaleague.client;

import org.javahispano.javaleague.client.mvp.AppPlacesHistoryMapper;
import org.javahispano.javaleague.client.mvp.views.LoginView;
import org.javahispano.javaleague.client.mvp.views.MyTacticView;
import org.javahispano.javaleague.client.mvp.views.RegisterView;
import org.javahispano.javaleague.client.mvp.views.WelcomeView;
import org.javahispano.javaleague.client.mvp.views.uibinder.LoginViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.MyTacticViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.RegisterViewImpl;
import org.javahispano.javaleague.client.mvp.views.uibinder.WelcomeViewImpl;
import org.javahispano.javaleague.client.service.AppUserService;
import org.javahispano.javaleague.client.service.AppUserServiceAsync;
import org.javahispano.javaleague.client.service.TacticUserServiceAsync;
import org.javahispano.javaleague.shared.domain.AppUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
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

	private static AppUser appUser;

	private final AppPlacesHistoryMapper historyMapper = GWT
			.create(AppPlacesHistoryMapper.class);

	private final AppUserServiceAsync appUserService = GWT
			.create(AppUserService.class);
	private final TacticUserServiceAsync tacticUserService = GWT
			.create(TacticUserService.class);

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
		return appUser;
	}

	@Override
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;

	}

	@Override
	public AppUserServiceAsync getAppUserService() {
		return appUserService;
	}

	@Override
	public TacticUserServiceAsync getTacticUserService() {
		return tacticUserService;
	}

}
