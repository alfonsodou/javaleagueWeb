package org.javahispano.javaleague.client.mvp.presenters;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author adou
 * 
 */
public class MenuPresenter implements Presenter {

	public interface Display {

		HasClickHandlers getRegisterLink();

		HasClickHandlers getLoginLink();
		
		HasClickHandlers getFrameWorkLink();
		
		HasClickHandlers getNavbarBrand();
		
		HasClickHandlers getWikiLink();

		Widget asWidget();

	}

	private final Display display;
	private final SimpleEventBus eventBus;

	public MenuPresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {

		this.display.getRegisterLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("MenuPresenter: Firing ShowRegisterUserEvent");
				//eventBus.fireEvent(new ShowRegisterUserEvent());
			}
		});

		this.display.getLoginLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("MenuPresenter: Firing ShowLoginEvent");
				//eventBus.fireEvent(new ShowLoginEvent());
			}
		});
		
		this.display.getFrameWorkLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("MenuPresenter: Firing ShowFrameWorkEvent");
				//eventBus.fireEvent(new ShowFrameWorkEvent());
			}
		});
		
		this.display.getNavbarBrand().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("MenuPresenter: Firing ShowHomeEvent");
				//eventBus.fireEvent(new ShowHomeEvent());
			}
		});		
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
