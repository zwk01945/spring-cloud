//package com.cloud.config.dynamicds;
//
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
///**
// * ClassName: DynamicDatasource
// * Description: 多数据源类型更新到Spring
// * date: 2020/7/21 13:12
// * @author Mr.zhang
// */
//public class DynamicDatasource extends AbstractRoutingDataSource {
//    @Override
//    protected Object determineCurrentLookupKey() {
//        return DataSourceContextHolder.getDbType();
//    }
//}
