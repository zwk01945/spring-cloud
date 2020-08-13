//package com.cloud.config.dynamicds.transcation;
//
//import com.example.common.dynamicds.DataSourceContextHolder;
//import org.apache.ibatis.logging.Log;
//import org.apache.ibatis.logging.LogFactory;
//import org.apache.ibatis.transaction.Transaction;
//import org.springframework.jdbc.CannotGetJdbcConnectionException;
//import org.springframework.jdbc.datasource.DataSourceUtils;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
//import static org.springframework.util.Assert.notNull;
//
///**************************************************************
// ***       S  T  A  G  E    多模块依赖项目                    ***
// **************************************************************
// *                                                            *
// *         Project Name : base                                *
// *                                                            *
// *         File Name : MultiDataSourceTransaction.java        *
// *                                                            *
// *         Programmer : Mr.zhang                              *
// *                                                            *
// *         Start Date : 2020/7/24 17:28                       *
// *                                                            *
// *         Last Update : 2020/7/24 17:28                      *
// *                                                            *
// *------------------------------------------------------------*
// * 功能:                                                       *
// *   多数据源事务的配置                                          *
// * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
//public class MultiDataSourceTransaction implements Transaction {
//
//    private static final Log LOGGER = LogFactory.getLog(MultiDataSourceTransaction.class);
//
//    private final DataSource dataSource;
//
//    private Connection mainConnection;
//
//    //当前线程中的主数据源名称
//    private String mainDatabaseIdentification;
//
//    //其余数据源
//    private ConcurrentMap<String, Connection> otherConnectionMap;
//
//
//    private boolean isConnectionTransactional;
//
//    private boolean autoCommit;
//
//    public MultiDataSourceTransaction(DataSource dataSource) {
//        notNull(dataSource, "No DataSource specified");
//        this.dataSource = dataSource;
//        otherConnectionMap = new ConcurrentHashMap<>();
//        mainDatabaseIdentification= DataSourceContextHolder.getDbType();
//    }
//
//
//    @Override
//    public Connection getConnection() throws SQLException {
//        String databaseIdentification = DataSourceContextHolder.getDbType();
//        if (databaseIdentification.equals(mainDatabaseIdentification)) {
//            if (mainConnection != null) return mainConnection;
//            else {
//                openMainConnection();
//                mainDatabaseIdentification =databaseIdentification;
//                return mainConnection;
//            }
//        } else {
//            if (!otherConnectionMap.containsKey(databaseIdentification)) {
//                try {
//                    Connection conn = dataSource.getConnection();
//                    otherConnectionMap.put(databaseIdentification, conn);
//                } catch (SQLException ex) {
//                    throw new CannotGetJdbcConnectionException("Could not get JDBC Connection", ex);
//                }
//            }
//            return otherConnectionMap.get(databaseIdentification);
//        }
//    }
//
//    private void openMainConnection() throws SQLException {
//        this.mainConnection = DataSourceUtils.getConnection(this.dataSource);
//        this.autoCommit = this.mainConnection.getAutoCommit();
//        this.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(this.mainConnection, this.dataSource);
//
//        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug(
//                    "JDBC Connection ["
//                            + this.mainConnection
//                            + "] will"
//                            + (this.isConnectionTransactional ? " " : " not ")
//                            + "be managed by Spring");
//        }
//    }
//
//
//    @Override
//    public void commit() throws SQLException {
//
//        if (this.mainConnection != null && !this.isConnectionTransactional && !this.autoCommit) {
//            if (LOGGER.isDebugEnabled()) {
//                LOGGER.debug("Committing JDBC Connection [" + this.mainConnection + "]");
//            }
//            this.mainConnection.commit();
//            for (Connection connection : otherConnectionMap.values()) {
//                connection.commit();
//            }
//        }
//
//    }
//
//    @Override
//    public void rollback() throws SQLException {
//        if (this.mainConnection != null && !this.isConnectionTransactional && !this.autoCommit) {
//            if (LOGGER.isDebugEnabled()) {
//                LOGGER.debug("Rolling back JDBC Connection [" + this.mainConnection + "]");
//            }
//            this.mainConnection.rollback();
//            for (Connection connection : otherConnectionMap.values()) {
//                connection.rollback();
//            }
//        }
//    }
//
//    @Override
//    public void close() throws SQLException {
//        DataSourceUtils.releaseConnection(this.mainConnection, this.dataSource);
//        for (Connection connection : otherConnectionMap.values()) {
//            DataSourceUtils.releaseConnection(connection, this.dataSource);
//        }
//    }
//
//    @Override
//    public Integer getTimeout() throws SQLException {
//        return null;
//    }
//}
