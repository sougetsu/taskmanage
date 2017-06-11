package com.sdmx.framework.service;

import com.sdmx.framework.entity.LogInfo;

public interface ILogService extends IService {
	public LogInfo create(LogInfo log);
}
