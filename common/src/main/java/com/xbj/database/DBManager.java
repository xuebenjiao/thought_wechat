package com.xbj.database;

import android.database.sqlite.SQLiteException;

import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.utils.LogUtils;
import com.thoughtwork.base.utils.ToastUtil;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

/**
 * Time :2019/11/4
 * Author:xbj
 * Description :数据库操作管理
 */
public abstract class DBManager<M, K> implements IDatabase<M, K> {

    @Override
    public boolean insert(@NotNull M m) {
        try {
            getAbstractDao().insert(m);
        } catch (SQLiteException e) {
            ToastUtil.show(BaseApplication.getmContext(),e.getMessage().toString());
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplace(@NotNull M m) {
        try {
            getAbstractDao().insertOrReplace(m);
        } catch (SQLiteException e) {
            LogUtils.e(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean insertInTx(@NotNull List<M> list) {
        try {
            getAbstractDao().insertInTx(list);
        } catch (SQLiteException e) {
            LogUtils.e(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean insertOrReplaceInTx(@NotNull List<M> list) {
        try {
            getAbstractDao().insertOrReplaceInTx(list);
        } catch (SQLiteException e) {
            LogUtils.e(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(@NotNull M m) {
        try {
            getAbstractDao().delete(m);
        } catch (SQLiteException e) {
            LogUtils.e(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey(@NotNull K key) {
        try {
            getAbstractDao().deleteByKey(key);
        } catch (SQLiteException e) {
            LogUtils.e(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteInTx(@NotNull List<M> list) {
        try {
            getAbstractDao().deleteInTx(list);
        } catch (SQLiteException e) {
            LogUtils.e(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAll() {
        try {
            getAbstractDao().deleteAll();
        } catch (SQLiteException e) {
            LogUtils.e(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean update(@NotNull M m) {
        try {
            getAbstractDao().update(m);
        } catch (SQLiteException e) {
            LogUtils.e(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateInTx(@NotNull List<M> list) {
        try {
            getAbstractDao().updateInTx(list);
        } catch (SQLiteException e) {
            LogUtils.e(e);
            return false;
        }
        return true;
    }

    @Override
    public M load(@NotNull K key) {
        try {
            return getAbstractDao().load(key);
        } catch (SQLiteException e) {
            LogUtils.e(e);
            return null;
        }
    }

    @Override
    public List<M> loadAll() {
        return getAbstractDao().loadAll();
    }

    @Override
    public boolean refresh(@NotNull M m) {
        try {
            getAbstractDao().refresh(m);
        } catch (SQLiteException e) {
            LogUtils.e(e);
            return false;
        }
        return true;
    }

    @Override
    public abstract AbstractDao<M, K> getAbstractDao();

}