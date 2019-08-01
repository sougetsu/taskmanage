package com.sdmx.certification.service;

import com.sdmx.certification.entity.Certification;
import com.sdmx.certification.vo.CertificationVO;
import com.sdmx.framework.vo.DataGrid;

public interface ICertificationService {
	public Certification create(CertificationVO certificationvo);
	public DataGrid listCertification(CertificationVO certificationvo);
	public CertificationVO getCertificationById(String id);
	public void remove(String id);
	public void editSubmit(CertificationVO certificationVO);
}
