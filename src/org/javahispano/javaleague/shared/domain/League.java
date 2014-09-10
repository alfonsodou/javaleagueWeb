package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.shared.AppLib;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

/**
 * 
 * @author adou
 *
 */
@Entity
public class League implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  
	@Id
	private Long id;
	
	private Long managerId;
	
	private String name;
	
	private String nameManager;
	
	private String description;
	
	private List<AppUser> appUsers;
	
	private Date creation;
	
	private Date updated;
	
	private Date startSignIn;
	
	private Date endSignIn;
	
	private String password;
	
	private Integer type;
	
	private Integer numberRounds;
	
	private Integer pointsForWin;
	
	private Integer pointsForTied;
	
	private Integer pointsForLost;
	
	private Integer numberMatchs;
	
	private Integer executedMatchs;
	
	private Boolean defaultLeague;
	
	@Index
	private int state;
	
	public League() {
		super();
		this.creation = new Date();
		this.updated = new Date();
		this.type = AppLib.LEAGUE_PUBLIC;
		this.numberRounds = AppLib.DEFAULT_NUMBER_ROUNDS;
		this.pointsForLost = AppLib.POINTS_FOR_LOST;
		this.pointsForTied = AppLib.POINTS_FOR_TIED;
		this.pointsForWin = AppLib.POINTS_FOR_WIN;
		this.numberMatchs = 0;
		this.executedMatchs = 0;
		this.state = AppLib.LEAGUE_INIT;
		this.defaultLeague = Boolean.FALSE;
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the managerId
	 */
	public Long getManagerId() {
		return managerId;
	}

	/**
	 * @param managerId the managerId to set
	 */
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	/**
	 * @return the creation
	 */
	public Date getCreation() {
		return creation;
	}

	/**
	 * @param creation the creation to set
	 */
	public void setCreation(Date creation) {
		this.creation = creation;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	
	/**
	 * @return the startSignIn
	 */
	public Date getStartSignIn() {
		return startSignIn;
	}

	/**
	 * @param startSignIn the startSignIn to set
	 */
	public void setStartSignIn(Date startSignIn) {
		this.startSignIn = startSignIn;
	}

	/**
	 * @return the endSignIn
	 */
	public Date getEndSignIn() {
		return endSignIn;
	}

	/**
	 * @param endSignIn the endSignIn to set
	 */
	public void setEndSignIn(Date endSignIn) {
		this.endSignIn = endSignIn;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the nameManager
	 */
	public String getNameManager() {
		return nameManager;
	}

	/**
	 * @param nameManager the nameManager to set
	 */
	public void setNameManager(String nameManager) {
		this.nameManager = nameManager;
	}

	public boolean isPublic() {
		return (this.type == AppLib.LEAGUE_PUBLIC);
	}
	
	/**
	 * @return the numberRounds
	 */
	public Integer getNumberRounds() {
		return numberRounds;
	}

	/**
	 * @param numberRounds the numberRounds to set
	 */
	public void setNumberRounds(Integer numberRounds) {
		this.numberRounds = numberRounds;
	}

	/**
	 * @return the pointsForWin
	 */
	public Integer getPointsForWin() {
		return pointsForWin;
	}

	/**
	 * @param pointsForWin the pointsForWin to set
	 */
	public void setPointsForWin(Integer pointsForWin) {
		this.pointsForWin = pointsForWin;
	}

	/**
	 * @return the pointsForTied
	 */
	public Integer getPointsForTied() {
		return pointsForTied;
	}

	/**
	 * @param pointsForTied the pointsForTied to set
	 */
	public void setPointsForTied(Integer pointsForTied) {
		this.pointsForTied = pointsForTied;
	}

	/**
	 * @return the pointsForLost
	 */
	public Integer getPointsForLost() {
		return pointsForLost;
	}

	/**
	 * @param pointsForLost the pointsForLost to set
	 */
	public void setPointsForLost(Integer pointsForLost) {
		this.pointsForLost = pointsForLost;
	}
	
	/**
	 * @return the numberMatchs
	 */
	public int getNumberMatchs() {
		return numberMatchs;
	}

	/**
	 * @param numberMatchs the numberMatchs to set
	 */
	public void setNumberMatchs(int numberMatchs) {
		this.numberMatchs = numberMatchs;
	}

	/**
	 * @return the executedMatchs
	 */
	public int getExecutedMatchs() {
		return executedMatchs;
	}

	/**
	 * @param executedMatchs the executedMatchs to set
	 */
	public void setExecutedMatchs(int executedMatchs) {
		this.executedMatchs = executedMatchs;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the appUsers
	 */
	public List<AppUser> getAppUsers() {
		return appUsers;
	}

	/**
	 * @param appUsers the appUsers to set
	 */
	public void setAppUsers(List<AppUser> appUsers) {
		this.appUsers = appUsers;
	}

	/**
	 * @param numberMatchs the numberMatchs to set
	 */
	public void setNumberMatchs(Integer numberMatchs) {
		this.numberMatchs = numberMatchs;
	}

	/**
	 * @param executedMatchs the executedMatchs to set
	 */
	public void setExecutedMatchs(Integer executedMatchs) {
		this.executedMatchs = executedMatchs;
	}

	/**
	 * @return the defaultLeague
	 */
	public Boolean getDefaultLeague() {
		return defaultLeague;
	}

	/**
	 * @param defaultLeague the defaultLeague to set
	 */
	public void setDefaultLeague(Boolean defaultLeague) {
		this.defaultLeague = defaultLeague;
	}
	
	
}
