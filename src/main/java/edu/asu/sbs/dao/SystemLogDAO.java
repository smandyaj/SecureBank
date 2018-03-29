package edu.asu.sbs.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.asu.sbs.model.SystemLog;

public interface SystemLogDAO {

	public void addSystemLog(SystemLog s);
	public List<SystemLog> getSystemLog();
}
