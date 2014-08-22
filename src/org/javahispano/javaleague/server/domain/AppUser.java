/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import java.util.Date;
import java.util.logging.Logger;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * @author adou
 * 
 */
@Entity
public class AppUser extends DatastoreObject {

	private static final Logger log = Logger.getLogger(AppUser.class.getName());

	private String appUserName;
	@Index
	private String email;
	private String password;
	private String token;
	private Date dateToken;
	private String locale;
	private Date lastActive;
	private boolean active;
	private Date lastLoginOn;
	
	public AppUser() {
		this.locale = "es";	
		this.active = false;
	}
	
	/**
	 * @return the lastActive
	 */
	public Date getLastActive() {
		return lastActive;
	}

	/**
	 * @param lastActive the lastActive to set
	 */
	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}

	public AppUser(String appUserName, String email) {
		this.appUserName = appUserName;
		this.email = email;
		this.locale = "es";
		this.active = false;
	}

	/**
	 * @return the appUserName
	 */
	public String getAppUserName() {
		return appUserName;
	}

	/**
	 * @param appUserName the appUserName to set
	 */
	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the dateToken
	 */
	public Date getDateToken() {
		return dateToken;
	}

	/**
	 * @param dateToken the dateToken to set
	 */
	public void setDateToken(Date dateToken) {
		this.dateToken = dateToken;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the lastLoginOn
	 */
	public Date getLastLoginOn() {
		return lastLoginOn;
	}

	/**
	 * @param lastLoginOn the lastLoginOn to set
	 */
	public void setLastLoginOn(Date lastLoginOn) {
		this.lastLoginOn = lastLoginOn;
	}

	
	
}
