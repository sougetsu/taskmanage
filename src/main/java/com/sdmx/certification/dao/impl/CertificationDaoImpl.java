package com.sdmx.certification.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.certification.dao.ICertificationDao;
import com.sdmx.certification.entity.Certification;
import com.sdmx.framework.dao.impl.BaseDaoImpl;

@Repository("certificationDao")
public class CertificationDaoImpl extends BaseDaoImpl<Certification> implements
		ICertificationDao {

}
