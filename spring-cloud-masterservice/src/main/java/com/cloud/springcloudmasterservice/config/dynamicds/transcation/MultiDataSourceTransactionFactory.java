//package com.cloud.config.dynamicds.transcation;
//
//import org.apache.ibatis.session.TransactionIsolationLevel;
//import org.apache.ibatis.transaction.Transaction;
//import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
//
//import javax.sql.DataSource;
//
///**
// * 创建MultiDatasoureTransaction的工厂类，交Spring管理
// */
//public class MultiDataSourceTransactionFactory extends SpringManagedTransactionFactory {
//    @Override
//    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
//        return new MultiDataSourceTransaction(dataSource);
//    }
//}
