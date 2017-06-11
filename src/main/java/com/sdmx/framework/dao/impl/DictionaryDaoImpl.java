package com.sdmx.framework.dao.impl;

import org.springframework.stereotype.Repository;

import com.sdmx.framework.dao.IDictionaryDao;
import com.sdmx.framework.entity.Dictionary;

@Repository("dictionaryDao")
public class DictionaryDaoImpl extends BaseDaoImpl<Dictionary> implements IDictionaryDao {

}
