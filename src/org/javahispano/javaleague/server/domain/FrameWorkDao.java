/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.domain.FrameWork;

/**
 * @author adou
 * 
 */
public class FrameWorkDao {
	public FrameWorkDao() {
		super();
	}

	public FrameWork save(FrameWork framework) {
		framework.setUpdated(new Date());
		ofy().save().entity(framework).now();
		return framework;
	}

	public FrameWork findById(Long id) {
		return ofy().load().type(FrameWork.class).id(id).now();
	}

	public FrameWork findDefaultFrameWork() {
		FrameWork frameWork = ofy().load().type(FrameWork.class)
				.filter("defaultFrameWork", Boolean.TRUE).first().now();

		return frameWork;
	}

	public List<FrameWork> findAllFrameWorks() {
		List<FrameWork> frameWorks = ofy().load().type(FrameWork.class)
				.filter("active", true).order("defaultFrameWork")
				.order("-updated").list();

		return frameWorks;
	}
}
