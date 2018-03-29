package edu.asu.sbs.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYSTEMLOG")
public class SystemLog {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name= "login_time")
	private Timestamp logTime;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "action")
	private String action;

	public Timestamp getLogTime() {
		return logTime;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public SystemLog(Timestamp logTime, String firstName, String lastName, String action) {
		super();
		this.logTime = logTime;
		this.firstName = firstName;
		this.lastName = lastName;
		this.action = action;
	}

	public SystemLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "logTime="+ this.logTime.toString()+", name="+this.firstName+",action="+this.action;
	}
	
	
	
}
