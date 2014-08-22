/**
 * 
 */
package org.javahispano.javaleague.client.mvp.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class ShowHomeEvent extends GwtEvent<ShowHomeEventHandler> {
	public static Type<ShowHomeEventHandler> TYPE = new Type<ShowHomeEventHandler>();
	private final Long userId;
	
	public ShowHomeEvent(Long id) {
		this.userId = id;
	}

	@Override
	public Type<ShowHomeEventHandler> getAssociatedType() {

		return TYPE;
	}

	@Override
	protected void dispatch(ShowHomeEventHandler handler) {
		handler.onShowHome(this);
	}

	public Long getUserId() {
		return userId;
	}
}
