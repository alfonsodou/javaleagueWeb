package org.javahispano.javaleague.client.mvp.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author adou
 *
 */
public class RPCInEvent extends GwtEvent<RPCInEventHandler> {
  public static Type<RPCInEventHandler> TYPE = new Type<RPCInEventHandler>();


  @Override public Type<RPCInEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override protected void dispatch(RPCInEventHandler handler) {
    handler.onRPCIn(this);
  }
}
