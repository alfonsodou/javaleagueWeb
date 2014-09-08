/**
 * 
 */
package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import org.javahispano.javaleague.shared.AppLib;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * @author adou
 *
 */
@Entity
public class TacticUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(TacticUser.class.getName());
	
	@Id
	private Long id;
	@Index
	private Long userId;
	private String teamName;
	private Date creation;
	private Date updated;
	private String fileNameJar;
	private String fileNameImage;
	private Long bytes;
	private Boolean valid;
	@Index
	private Integer state;
	
	public TacticUser() {
		this.valid = false;
		this.creation = new Date();
		this.updated = new Date();
		this.bytes = 0L;
		this.state = AppLib.FRIENDLY_MATCH_OK;
		this.fileNameJar = AppLib.NO_FILE;
	}
	
	public TacticUser(Long userId, String teamName) {
		this.valid = false;
		this.creation = new Date();
		this.updated = new Date();
		this.bytes = 0L;
		this.userId = userId;
		this.teamName = teamName;
		this.state = AppLib.FRIENDLY_MATCH_OK;
		this.fileNameJar = AppLib.NO_FILE;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
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
	 * @return the fileName
	 */
	public String getFileNameJar() {
		return fileNameJar;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileNameJar(String fileNameJar) {
		this.fileNameJar = fileNameJar;
	}

	/**
	 * @return the bytes
	 */
	public Long getBytes() {
		return bytes;
	}

	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(Long bytes) {
		this.bytes = bytes;
	}

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return the fileNameImage
	 */
	public String getFileNameImage() {
		return fileNameImage;
	}

	/**
	 * @param fileNameImage the fileNameImage to set
	 */
	public void setFileNameImage(String fileNameImage) {
		this.fileNameImage = fileNameImage;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the valid
	 */
	public Boolean getValid() {
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
}
