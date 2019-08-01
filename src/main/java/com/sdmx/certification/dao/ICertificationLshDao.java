package com.sdmx.certification.dao;

import com.sdmx.certification.entity.CertificationLsh;
import com.sdmx.framework.dao.IBaseDao;

public interface ICertificationLshDao extends IBaseDao<CertificationLsh> {
	public String getCertificationId();
}
