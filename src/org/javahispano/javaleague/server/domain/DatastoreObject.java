package org.javahispano.javaleague.server.domain;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.OnSave;

public class DatastoreObject {
	@Id
	private Long id;
	private Integer version = 0;

	/**
	 * Auto-increment version # whenever persisted
	 */
	@OnSave
	void onSave() {
		this.version++;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
