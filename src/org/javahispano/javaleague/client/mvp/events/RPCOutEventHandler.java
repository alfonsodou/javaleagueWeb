package org.javahispano.javaleague.client.mvp.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @author adou
 *
 */
public interface RPCOutEventHandler extends EventHandler {
  void onRPCOut(RPCOutEvent event);
}
