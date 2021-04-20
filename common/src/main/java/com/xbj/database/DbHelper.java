package com.xbj.database;

import android.content.Context;


import com.xbj.greendao.gen.CustomeSignTypeModelDao;
import com.xbj.greendao.gen.DaoMaster;
import com.xbj.greendao.gen.DaoSession;
import com.xbj.greendao.gen.LineFrequencyModelDao;
import com.xbj.greendao.gen.LineRelatedModelDao;
import com.xbj.greendao.gen.NextStationModelDao;
import com.xbj.greendao.gen.ScanResultModelDao;
import com.xbj.greendao.gen.UserLineRelatedModelDao;
import com.xbj.mode.AuthorModel;
import com.xbj.mode.LineRelatedModel;
import com.xbj.mode.NextStationModel;
import com.xbj.mode.ScanResultModel;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;

/**
 * @Description: 数据库操作类，由于greenDao的特殊性，不能在框架中搭建，
 * 所有数据库操作都可以参考该类实现自己的数据库操作管理类，不同的Dao实现
 * 对应的getAbstractDao方法就行。
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 17/1/18 23:18.
 */
public class DbHelper {
    //数据库名称
    private static final String DB_NAME = "delivery.db";
    private static DbHelper instance;
    private static DBManager<ScanResultModel, Long> uScanManager;
    private static  DBManager<AuthorModel, Long> author;
    private static  DBManager<LineRelatedModel, Long> lineRelatedModelLongDBManager;
    private static  DBManager<NextStationModel, Long> nextStationModelLongDBManager;
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
        mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null){
            @Override
            public void onUpgrade(Database db, int oldVersion, int newVersion) {
                if(oldVersion < newVersion){//数据库版本升级
                    MigrationHelper.migrate((StandardDatabase) db,
                            ScanResultModelDao.class,
                            LineRelatedModelDao.class,
                            NextStationModelDao.class,
                            UserLineRelatedModelDao.class,
                            CustomeSignTypeModelDao.class,
                            LineFrequencyModelDao.class);
                }
            }
        };
        mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        //创建表
        DaoMaster.createAllTables(mDaoMaster.getDatabase(),true);
        mDaoSession = mDaoMaster.newSession();
    }

    public void init(Context context, String dbName) {
        mHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DBManager<AuthorModel, Long> author() {
        if (author == null) {
            author = new DBManager<AuthorModel, Long>() {
                @Override
                public AbstractDao<AuthorModel, Long> getAbstractDao() {
                    return mDaoSession.getAuthorModelDao();
                }
            };
        }
        return author;
    }

    public DBManager<ScanResultModel, Long> getuScanManager() {
        if (uScanManager == null) {
            uScanManager = new DBManager<ScanResultModel, Long>() {
                @Override
                public AbstractDao<ScanResultModel, Long> getAbstractDao() {
                    return mDaoSession.getScanResultModelDao();
                }
            };
        }
        return uScanManager;
    }

    public  DBManager<LineRelatedModel, Long> getLineRelatedModelLongDBManager() {
        if (lineRelatedModelLongDBManager == null) {
            lineRelatedModelLongDBManager = new DBManager<LineRelatedModel, Long>() {
                @Override
                public AbstractDao<LineRelatedModel, Long> getAbstractDao() {
                    return mDaoSession.getLineRelatedModelDao();
                }
            };
        }
        return lineRelatedModelLongDBManager;
    }

    public  DBManager<NextStationModel, Long> getNextStationModelLongDBManager() {
        if (nextStationModelLongDBManager == null) {
            nextStationModelLongDBManager = new DBManager<NextStationModel, Long>() {
                @Override
                public AbstractDao<NextStationModel, Long> getAbstractDao() {
                    return mDaoSession.getNextStationModelDao();
                }
            };
        }
        return nextStationModelLongDBManager;
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
