/**
 * 
 */
package org.javahispano.javaleague.server.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.MatchFriendlyService;
import org.javahispano.javaleague.server.domain.AppUserDao;
import org.javahispano.javaleague.server.domain.MatchFriendlyDao;
import org.javahispano.javaleague.server.domain.TacticUserDao;
import org.javahispano.javaleague.server.utils.Utils;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.AppUser;
import org.javahispano.javaleague.shared.domain.MatchFriendly;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.QueueStatistics;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 *
 */
public class MatchFriendlyServiceImpl extends RemoteServiceServlet implements
		MatchFriendlyService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(MatchFriendlyServiceImpl.class.getName());

	@Override
	public List<MatchFriendly> getAllFriendlyMatchsByTactic(Long tacticId) {
		List<MatchFriendly> matchs = null;
		try {
			MatchFriendlyDao dao = new MatchFriendlyDao();
			matchs = dao.findAllByTactic(tacticId);
			bubbleSort(matchs);
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}

		return matchs;
	}

	private void bubbleSort(List<MatchFriendly> array) {
		boolean swapped = true;
		int j = 0;
		MatchFriendly tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < array.size() - j; i++) {
				if (array.get(i).getVisualization()
						.compareTo(array.get(i + 1).getVisualization()) > 0) {
					tmp = array.get(i);
					array.set(i, array.get(i + 1));
					array.set(i + 1, tmp);
					swapped = true;
				}
			}
		}
	}

	@Override
	public MatchFriendly dispatchMatch(Long tacticID) {
		TacticUser tactic = null;
		TacticUserDao tacticUserDAO = new TacticUserDao();
		AppUserDao appUserDAO = new AppUserDao();
		MatchFriendlyDao matchFriendlyDAO = new MatchFriendlyDao();
		try {
			tactic = tacticUserDAO.fetch(tacticID);
			AppUser currentUser = appUserDAO.fetch(tactic.getUserId());
			List<MatchFriendly> matchList = matchFriendlyDAO
					.getMatchsState(AppLib.MATCH_FRIENDLY_WAITING);

			if ((matchList != null) && (matchList.size() > 0)) {
				MatchFriendly match = matchList.get(0);

				if (match.getLocalTeamId() == 0L) {

					match.setNameLocal(tactic.getTeamName());
					match.setNameLocalManager(currentUser.getAppUserName());
					match.setLocalTeamId(tactic.getId());
				} else {

					match.setNameForeign(tactic.getTeamName());
					match.setNameVisitingManager(currentUser.getAppUserName());
					match.setVisitingTeamId(tactic.getId());
				}

				QueueStatistics queueStatistics = QueueFactory.getQueue(
						AppLib.QUEUE_FRIENDLY).fetchStatistics();

				match.setExecution(new Date());
				match.setVisualization(Utils
						.addMinutesToDate(
								new Date(),
								AppLib.MINUTES_EXECUTION_MATCH
										+ (AppLib.MINUTES_EXECUTION_MATCH * queueStatistics
												.getNumTasks())));
				matchFriendlyDAO.save(match);

				Queue queue = QueueFactory.getQueue(AppLib.QUEUE_FRIENDLY);
				queue.add(TaskOptions.Builder.withUrl("/playMatchFriendly")
						.param("matchID", match.getId().toString()));
			} else {
				MatchFriendly match = new MatchFriendly();
				int result = ((int) (Math.random() * 10));

				if (result % 2 == 0) {

					match.setNameLocal(tactic.getTeamName());
					match.setNameLocalManager(currentUser.getAppUserName());
					match.setLocalTeamId(tactic.getId());
				} else {
					
					match.setNameForeign(tactic.getTeamName());
					match.setNameVisitingManager(currentUser.getAppUserName());
					match.setVisitingTeamId(tactic.getId());
				}

				match.setState(AppLib.MATCH_FRIENDLY_WAITING);
				matchFriendlyDAO.save(match);
			}

			tactic.setState(AppLib.FRIENDLY_MATCH_SCHEDULED);
			tacticUserDAO.save(tactic);
		} catch (Exception e) {
			logger.warning(Utils.stackTraceToString(e));
		}

		return null;
	}

}
