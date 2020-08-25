###nacos配置持久化
#####一、nacos配置(windows版本)
- 在任意一个nacos下的conf下找到nacos-mysql.sql文件
- 在mysql中建库名为nacos_config，然后执行sql
- 修改nacos下的conf下的application.properties中的db设置如下


    spring.datasource.platform=mysql
    
    ### Count of DB:
    db.num=1
    
    ### Connect URL of DB:
    db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&serverTimezone=UTC
    db.user=root
    db.password=123456
    
- 重新启动所有节点即可，然后所有配置过的文件都会存到数据库,性能稍微差点，可以搭建数据库集群