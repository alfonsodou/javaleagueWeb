package org.javahispano.javaleague.client.mvp.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author adou
 *
 */
public class LogoutEvent extends GwtEvent<LogoutEventHandler> {
  public static Type<LogoutEventHandler> TYPE = new Type<LogoutEventHandler>();

  public LogoutEvent() {
  }

  @Override public Type<LogoutEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override protected void dispatch(LogoutEventHandler handler) {
    handler.onLogout(this);
  }
}
