package com.example.demo.framework.mybatis.toolkit;

import lombok.SneakyThrows;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SqlHelper {

    @SneakyThrows
    public static boolean executeBatch(SqlSessionFactory sqlSessionFactory, Log log,
                                       Consumer<SqlSession> consumer) {
        SqlSessionHolder sqlSessionHolder = (SqlSessionHolder) TransactionSynchronizationManager
            .getResource(sqlSessionFactory);
        boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
        if (sqlSessionHolder != null) {
            SqlSession sqlSession = sqlSessionHolder.getSqlSession();
            //原生无法支持执行器切换，当存在批量操作时，会嵌套两个session的，优先commit上一个session
            //按道理来说，这里的值应该一直为false。
            sqlSession.commit(!transaction);
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        if (!transaction) {
            log.warn("SqlSession [" + sqlSession + "] Transaction not enabled");
        }
        try {
            consumer.accept(sqlSession);
            //非事务情况下，强制commit。
            sqlSession.commit(!transaction);
            return true;
        } catch (Throwable t) {
            sqlSession.rollback();
            Throwable unwrapped = ExceptionUtil.unwrapThrowable(t);
            if (unwrapped instanceof PersistenceException) {
                MyBatisExceptionTranslator myBatisExceptionTranslator = new MyBatisExceptionTranslator(
                    sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true);
                Throwable throwable = myBatisExceptionTranslator
                    .translateExceptionIfPossible((PersistenceException) unwrapped);
                if (throwable != null) {
                    throw throwable;
                }
            }
            throw new RuntimeException(unwrapped);
        } finally {
            sqlSession.close();
        }
    }

    public static <E> boolean executeBatch(SqlSessionFactory sqlSessionFactory, Log log,
                                           Collection<E> list, int batchSize,
                                           BiConsumer<SqlSession, E> consumer) {
        Assert.isTrue(batchSize >= 1, "batchSize must not be less than one");
        return !CollectionUtils.isEmpty(list)
               && executeBatch(sqlSessionFactory, log, sqlSession -> {
                   int size = list.size();
                   int idxLimit = Math.min(batchSize, size);
                   int i = 1;
                   for (E element : list) {
                       consumer.accept(sqlSession, element);
                       if (i == idxLimit) {
                           sqlSession.flushStatements();
                           idxLimit = Math.min(idxLimit + batchSize, size);
                       }
                       i++;
                   }
               });
    }

}
