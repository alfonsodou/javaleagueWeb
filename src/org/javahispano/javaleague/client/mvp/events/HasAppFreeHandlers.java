package org.javahispano.javaleague.client.mvp.events;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;


public interface HasAppFreeHandlers extends HasHandlers {
  public HandlerRegistration addHasAppFreeHandler(AppFreeHandler handler); 
}
