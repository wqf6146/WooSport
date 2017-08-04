package com.yhrjkf.woyundong.bean.db;

import android.content.Context;

import com.vise.xsnow.database.DBManager;

import org.greenrobot.greendao.AbstractDao;

/**
 * @Description: 数据库操作类，由于greenDao的特殊性，不能在框架中搭建，
 * 所有数据库操作都可以参考该类实现自己的数据库操作管理类，不同的Dao实现
 * 对应的getAbstractDao方法就行。
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 17/1/18 23:18.
 */
public class DbHelper {
    private static final String DB_NAME = "woosport.db";//数据库名称
    private static DbHelper instance;
    private static DBManager<CSportInfoBean, Long> cSportInfoBeanLongDBManager;
    private static DBManager<CSportStatusBean, Long> cSportStatusBeanLongDBManager;
    private static DBManager<CUserBean,Long> cUserBeanLongDBManager;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DbHelper() {

    }

    public static DbHelper getInstance() {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null) {
                    instance = new DbHelper();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public void init(Context context, String dbName) {
        mHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DBManager<CSportStatusBean, Long> cSportStatusBeanLongDBManager() {
        if (cSportStatusBeanLongDBManager == null) {
            cSportStatusBeanLongDBManager = new DBManager<CSportStatusBean, Long>(){
                @Override
                public AbstractDao<CSportStatusBean, Long> getAbstractDao() {
                    return mDaoSession.getCSportStatusBeanDao();
                }
            };
        }
        return cSportStatusBeanLongDBManager;
    }

    public DBManager<CSportInfoBean, Long> cSportInfoBeanLongDBManager() {
        if (cSportInfoBeanLongDBManager == null) {
            cSportInfoBeanLongDBManager = new DBManager<CSportInfoBean, Long>(){
                @Override
                public AbstractDao<CSportInfoBean, Long> getAbstractDao() {
                    return mDaoSession.getCSportInfoBeanDao();
                }
            };
        }
        return cSportInfoBeanLongDBManager;
    }

    public DBManager<CUserBean, Long> cUserBeanLongDBManager() {
        if (cUserBeanLongDBManager == null) {
            cUserBeanLongDBManager = new DBManager<CUserBean, Long>(){
                @Override
                public AbstractDao<CUserBean, Long> getAbstractDao() {
                    return mDaoSession.getCUserBeanDao();
                }
            };
        }
        return cUserBeanLongDBManager;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void clear() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    public void close() {
        clear();
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }
}
