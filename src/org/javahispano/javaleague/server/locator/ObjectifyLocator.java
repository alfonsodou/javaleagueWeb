package org.javahispano.javaleague.server.locator;

import static org.javahispano.javaleague.server.service.OfyService.ofy;

import org.javahispano.javaleague.server.domain.DatastoreObject;

import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * Generic @Locator for objects that extend DatastoreObject
 */
public class ObjectifyLocator extends Locator<DatastoreObject, Long> {
	@Override
	public DatastoreObject create(Class<? extends DatastoreObject> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DatastoreObject find(Class<? extends DatastoreObject> clazz, Long id) {
		return ofy().load().type(clazz).id(id).now();
	}

	@Override
	public Class<DatastoreObject> getDomainType() {
		// Never called
		return null;
	}

	@Override
	public Long getId(DatastoreObject domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<Long> getIdType() {
		return Long.class;
	}

	@Override
	public Object getVersion(DatastoreObject domainObject) {
		return domainObject.getVersion();
	}
}
