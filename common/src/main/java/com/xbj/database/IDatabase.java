package com.xbj.database;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Collection;
import java.util.List;

/**
 * Time :2019/11/4
 * Author:xbj
 * Description :数据操库作接口
 */
public interface  IDatabase<M, K> {
        boolean insert(@NotNull M m);

        boolean insertOrReplace(@NotNull M m);

        boolean insertInTx(@NotNull List<M> list);

        boolean insertOrReplaceInTx(@NotNull List<M> list);

        boolean delete(@NotNull M m);

        boolean deleteByKey(@NotNull K key);

        boolean deleteInTx(@NotNull List<M> list);

        boolean deleteAll();

        boolean update(@NotNull M m);

        boolean updateInTx(@NotNull List<M> list);

        M load(@NotNull K key);

        List<M> loadAll();
        boolean refresh(@NotNull M m);
        AbstractDao<M, K> getAbstractDao();


        }
