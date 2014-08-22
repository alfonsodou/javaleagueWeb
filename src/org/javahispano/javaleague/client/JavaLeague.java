/**
 * 
 */
package org.javahispano.javaleague.client;

import org.javahispano.javaleague.client.mvp.AppActivityMapper;
import org.javahispano.javaleague.client.mvp.events.AppBusyEvent;
import org.javahispano.javaleague.client.mvp.events.AppBusyHandler;
import org.javahispano.javaleague.client.mvp.events.AppFreeEvent;
import org.javahispano.javaleague.client.mvp.events.AppFreeHandler;
import org.javahispano.javaleague.client.mvp.places.WelcomePlace;
import org.javahispano.javaleague.client.mvp.ui.BusyIndicator;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author adou
 * 
 */
public class JavaLeague implements EntryPoint {
	private EventBus eventBus;
	private SimplePanel appWidget = new SimplePanel();
	private Place defaultPlace = new WelcomePlace();

	@Override
	public void onModuleLoad() {

		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		eventBus = clientFactory.getEventBus();

		ActivityMapper appActivityMapper = new AppActivityMapper(clientFactory);
		ActivityManager appActivityManager = new ActivityManager(
				appActivityMapper, eventBus);
		appActivityManager.setDisplay(appWidget);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper

		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				clientFactory.getHistoryMapper());
		historyHandler.register(clientFactory.getPlaceController(), eventBus,
				defaultPlace);

		RootPanel.get().add(appWidget);
		// Goes to the place represented on URL else default place
		historyHandler.handleCurrentHistory();

		bind();

	}

	private void bind() {

		// Listen for AppBusy events on the event bus
		eventBus.addHandler(AppBusyEvent.getType(), new AppBusyHandler() {
			public void onAppBusyEvent(AppBusyEvent event) {
				BusyIndicator.busy();
			}
		});

		// Listen for AppFree events on the event bus
		eventBus.addHandler(AppFreeEvent.getType(), new AppFreeHandler() {
			public void onAppFreeEvent(AppFreeEvent event) {
				BusyIndicator.free();
			}
		});
	}

	private String buildStackTrace(Throwable t, String log) {
		// return "disabled";
		if (t != null) {
			log += t.getClass().toString();
			log += t.getMessage();
			//
			StackTraceElement[] stackTrace = t.getStackTrace();
			if (stackTrace != null) {
				StringBuffer trace = new StringBuffer();

				for (int i = 0; i < stackTrace.length; i++) {
					trace.append(stackTrace[i].getClassName() + "."
							+ stackTrace[i].getMethodName() + "("
							+ stackTrace[i].getFileName() + ":"
							+ stackTrace[i].getLineNumber());
				}

				log += trace.toString();
			}
			//
			Throwable cause = t.getCause();
			if (cause != null && cause != t) {

				log += buildStackTrace(cause, "CausedBy:\n");

			}
		}
		return log;
	}
}