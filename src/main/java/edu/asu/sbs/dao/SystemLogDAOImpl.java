package edu.asu.sbs.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.sbs.model.SystemLog;

@Repository("systemLogDAO")
public class SystemLogDAOImpl implements SystemLogDAO{
	
	
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(SystemLogDAOImpl.class);
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addSystemLog(SystemLog systemLog) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(systemLog);
		logger.info("System log saved!");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemLog> getSystemLog() {
		// TODO Auto-generated method stub
		System.out.println("System log is being fetched");
		Session session = this.sessionFactory.getCurrentSession();
		List<SystemLog> systemLogList = session.createQuery("from SystemLog").list();
		System.out.println(systemLogList.size());
		for(SystemLog systemLog : systemLogList) {
			logger.info(systemLog.toString());
			System.out.println(systemLog.toString());
		}
		
		return systemLogList;
	}

}
