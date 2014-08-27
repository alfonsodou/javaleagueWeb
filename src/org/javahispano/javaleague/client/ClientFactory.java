/**
 * 
 */
package org.javahispano.javaleague.client;

import org.javahispano.javaleague.client.mvp.AppPlacesHistoryMapper;
import org.javahispano.javaleague.client.mvp.views.LoginView;
import org.javahispano.javaleague.client.mvp.views.MyTacticView;
import org.javahispano.javaleague.client.mvp.views.RegisterView;
import org.javahispano.javaleague.client.mvp.views.WelcomeView;
import org.javahispano.javaleague.shared.proxy.AppUserProxy;
import org.javahispano.javaleague.shared.service.JavaLeagueRequestFactory;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author adou
 * 
 */
public interface ClientFactory {
	public EventBus getEventBus();

	public PlaceController getPlaceController();

	public JavaLeagueRequestFactory getRequestFactory();
	
	public AppPlacesHistoryMapper getHistoryMapper();
	
	public WelcomeView getWelcomeView();

	public RegisterView getRegisterView();
	
	public LoginView getLoginView();
	
	public MyTacticView getMyTacticView();
	
	public AppUserProxy getAppUser();
	
	public void setAppUser(AppUserProxy appUser);
}
