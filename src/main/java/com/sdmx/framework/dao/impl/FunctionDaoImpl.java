package com.sdmx.framework.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.IFunctionDao;
import com.sdmx.framework.entity.Function;

@Repository("functionDao")
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao {

}
