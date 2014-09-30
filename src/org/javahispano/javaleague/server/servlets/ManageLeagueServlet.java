/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.AppUserDao;
import org.javahispano.javaleague.server.domain.LeagueDao;
import org.javahispano.javaleague.server.domain.MatchLeagueDao;
import org.javahispano.javaleague.server.domain.TacticUserDao;
import org.javahispano.javaleague.server.utils.Utils;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.MatchLeague;
import org.javahispano.javaleague.shared.domain.TacticUser;

/**
 * @author adou
 *
 */
public class ManageLeagueServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LeagueDao leagueDao = new LeagueDao();
	private MatchLeagueDao matchDao = new MatchLeagueDao();
	private TacticUserDao tacticUserDao = new TacticUserDao();
	private AppUserDao appUserDao = new AppUserDao();
	private static final Logger log = Logger
			.getLogger(ManageLeagueServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String action = req.getParameter("action");

		if (action.equals("add")) {
			League league = new League();

			try {
				league.setName(req.getParameter("name"));
				league.setDefaultLeague(Boolean.TRUE);

				leagueDao.save(league);
			} catch (Exception e) {
				log.warning(Utils.stackTraceToString(e));
			}
		}
	}

	public League createCalendarLeague(League league, Date init) {
		int n = league.getAppUsers().size();
		int[][][] temp = crearLiguilla(n);

		int home, away;
		boolean swap;
		int partidos = n * (n - 1) / 2;
		int fechas = partidos / (n / 2);
		int partidosPorFecha = partidos / fechas;
		MatchLeague match;
		Date start = init;
		int indexDay = 0;
		TacticUser local;
		TacticUser visiting;

		log.warning("Equipos: " + n);
		log.warning("Total partidos: " + partidos);
		log.warning("Total fechas: " + fechas);
		log.warning("Partidos por fecha: " + partidosPorFecha);

		league.setNumberMatchs(partidos);
		league.setState(AppLib.LEAGUE_EXECUTION);
		for (int f = 0; f < league.getNumberRounds(); f++) {
			if (f % 2 == 0) { // es par
				swap = true;
			} else {
				swap = false;
			}

			for (int round = 0; round < fechas; round++) {
				log.warning("Fecha: " + round);

				for (int m = 0; m < partidosPorFecha; m++) {
					log.warning("Fecha: " + round + " :: Partido: " + m);

					match = new MatchLeague();
					match.setLeagueId(league.getId());
					match.setExecution(addMinutesToDate(start, -120));
					match.setVisualization(start);

					int found[] = new int[] { temp[round][m][0],
							temp[round][m][1] };

					if (swap) {
						home = found[1];
						away = found[0];
					} else {
						home = found[0];
						away = found[1];
					}

					local = tacticUserDao.fetch(league.getAppUsers().get(home));
					visiting = tacticUserDao.fetch(league.getAppUsers().get(
							away));

					match.setLocalTeamId(league.getAppUsers().get(home));
					match.setVisitingTeamId(league.getAppUsers().get(away));
					match.setNameLocal(local.getTeamName());
					match.setLocalTeamId(league.getAppUsers().get(home));
					match.setNameForeign(visiting.getTeamName());
					match.setNameLocalManager(appUserDao.fetch(
							local.getUserId()).getAppUserName());
					match.setNameVisitingManager(appUserDao.fetch(
							visiting.getUserId()).getAppUserName());
					match.setVisitingTeamId(league.getAppUsers().get(away));
					match = matchDao.save(match);
				}

				start = getNextDate(start, days.get(indexDay));

			}
		}

		league = leagueDAO.save(league);

		return league;
	}

	/**
	 * Metodo practico para crear liguillas todos contra todos, se debe indicar
	 * 'n' como la cantidad de equipos. Retorna un array donde: el primer indice
	 * representa la fecha el segundo representa el partido de la fecha y el
	 * tercer indice 0: equipo local y 1: la visita
	 * 
	 * Nota: No son partidos de ida y vuelta
	 * 
	 * @param n
	 * @return
	 */
	public static int[][][] crearLiguilla(int n) {
		boolean impar = n % 2 == 1;
		if (impar) {
			n++;
		}
		int ppf = n / 2;// partidos por fecha
		int p = n * (n - 1) / 2;// partidos total
		int f = p / ppf;// fechas
		int tmp[][][] = new int[f][impar ? ppf - 1 : ppf][2];
		int subTmp[][] = new int[ppf][2];
		int[][] camino = new int[2 * ppf - 1][2];
		int x = 1;
		int y = 0;
		boolean avanza = true;
		for (int i = 0; i < camino.length; i++) {
			camino[i][0] = x;
			camino[i][1] = y;
			if (avanza) {
				x++;
				if (x >= ppf) {
					x--;
					y++;
					avanza = false;
				}
			} else {
				x--;
			}
		}
		for (int i = 0; i < f; i++) {
			for (int j = 0; j < ppf; j++) {
				subTmp[j][0] = -1;
				subTmp[j][1] = -1;
			}
			subTmp[0][0] = 0;
			for (int j = 0; j < camino.length; j++) {
				x = camino[j][0];
				y = camino[j][1];
				subTmp[x][y] = (j + i) % (n - 1) + 1;
			}
			int k = 0;
			for (int j = 0; j < ppf; j++) {
				if (!impar
						|| (impar && subTmp[j][0] != n - 1 && subTmp[j][1] != n - 1)) {
					if (((subTmp[j][0] + subTmp[j][1]) % 2) == 0) {
						int temp = subTmp[j][0];
						subTmp[j][0] = subTmp[j][1];
						subTmp[j][1] = temp;
					}
					int l = subTmp[j][0];
					int v = subTmp[j][1];
					boolean invertir = false;
					if (l >= v) {
						invertir = (l + v) % 2 == 0;
					} else {
						invertir = (l + v + 1) % 2 == 0;
					}
					if (invertir) {
						tmp[i][k][0] = v;
						tmp[i][k][1] = l;
					} else {
						tmp[i][k][0] = l;
						tmp[i][k][1] = v;
					}
					k++;
				}
			}
		}
		return tmp;
	}

	/**
	 * Agrega o quita minutos a una fecha dada. Para quitar minutos hay que
	 * sumarle valores negativos.
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	private static Date addMinutesToDate(Date date, int minutes) {
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		calendarDate.add(Calendar.MINUTE, minutes);
		return calendarDate.getTime();
	}

	private static Date getNextDate(Date date, int day) {
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		calendarDate.add(Calendar.MINUTE, 1440);
		while (calendarDate.get(Calendar.DAY_OF_WEEK) != day) {
			calendarDate.add(Calendar.MINUTE, 1440);
		}

		return calendarDate.getTime();
	}

}
