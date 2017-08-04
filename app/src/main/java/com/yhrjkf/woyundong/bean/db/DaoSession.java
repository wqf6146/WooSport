package com.yhrjkf.woyundong.bean.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.yhrjkf.woyundong.bean.db.CSportInfoBean;
import com.yhrjkf.woyundong.bean.db.CSportStatusBean;
import com.yhrjkf.woyundong.bean.db.CUserBean;

import com.yhrjkf.woyundong.bean.db.CSportInfoBeanDao;
import com.yhrjkf.woyundong.bean.db.CSportStatusBeanDao;
import com.yhrjkf.woyundong.bean.db.CUserBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cSportInfoBeanDaoConfig;
    private final DaoConfig cSportStatusBeanDaoConfig;
    private final DaoConfig cUserBeanDaoConfig;

    private final CSportInfoBeanDao cSportInfoBeanDao;
    private final CSportStatusBeanDao cSportStatusBeanDao;
    private final CUserBeanDao cUserBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cSportInfoBeanDaoConfig = daoConfigMap.get(CSportInfoBeanDao.class).clone();
        cSportInfoBeanDaoConfig.initIdentityScope(type);

        cSportStatusBeanDaoConfig = daoConfigMap.get(CSportStatusBeanDao.class).clone();
        cSportStatusBeanDaoConfig.initIdentityScope(type);

        cUserBeanDaoConfig = daoConfigMap.get(CUserBeanDao.class).clone();
        cUserBeanDaoConfig.initIdentityScope(type);

        cSportInfoBeanDao = new CSportInfoBeanDao(cSportInfoBeanDaoConfig, this);
        cSportStatusBeanDao = new CSportStatusBeanDao(cSportStatusBeanDaoConfig, this);
        cUserBeanDao = new CUserBeanDao(cUserBeanDaoConfig, this);

        registerDao(CSportInfoBean.class, cSportInfoBeanDao);
        registerDao(CSportStatusBean.class, cSportStatusBeanDao);
        registerDao(CUserBean.class, cUserBeanDao);
    }
    
    public void clear() {
        cSportInfoBeanDaoConfig.clearIdentityScope();
        cSportStatusBeanDaoConfig.clearIdentityScope();
        cUserBeanDaoConfig.clearIdentityScope();
    }

    public CSportInfoBeanDao getCSportInfoBeanDao() {
        return cSportInfoBeanDao;
    }

    public CSportStatusBeanDao getCSportStatusBeanDao() {
        return cSportStatusBeanDao;
    }

    public CUserBeanDao getCUserBeanDao() {
        return cUserBeanDao;
    }

}
