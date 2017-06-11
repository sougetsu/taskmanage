package com.sdmx.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdmx.framework.dao.ILogDao;
import com.sdmx.framework.entity.LogInfo;
import com.sdmx.framework.service.ILogService;

@Service("logService")
public class LogServiceImpl implements ILogService {
	
	@Autowired
	private ILogDao logDao;
	
	@Override
    @Transactional
	public LogInfo create(LogInfo log) {
		try{
			logDao.save(log);
		}catch(Exception e){
			System.out.println("error-service");
			e.printStackTrace();
		}
		return log;
	}

}
