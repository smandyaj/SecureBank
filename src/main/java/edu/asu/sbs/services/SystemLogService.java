package edu.asu.sbs.services;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.sbs.model.SystemLog;

public interface SystemLogService {
	
	public void addSystemLog(SystemLog s);
	public List<SystemLog> getSystemLog();
}
