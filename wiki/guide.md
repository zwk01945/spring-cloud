### 使用手册

#####一、介绍
- 项目结构为分布式,分模块依赖,根据功能分模块
- 模块包括公共模块依赖包，图片服务，消息推送服务，基础服务若干，web服务端接口服务，网关服务限流模块
- 技术栈包括：
    - SpringCloudAlibaba，Seata分布式事务
    - Minio作为图片服务器，Aliyun短信推送
    - Netty结合Websocket实现高性能长连接，redis集群，Druid数据源连接池管理连接
    - SpringAop自定义redis切面(读写锁控制)和多数据源切面类
    - 全局异常拦截处理，全局Web请求字符拦截过滤处理，静态资源拦截处理
#####二、模块指引

<table>
        <tr>
            <th>序号</th>
            <th>服务名</th>
            <th>端口</th>
            <th>描述</th>
        </tr>
        <tr>
            <th>1</th>
            <th>spring-cloud-common</th>
            <th>无</th>
            <th>公共模块依赖组件</th>
        </tr>
        <tr>
            <th>2</th>
            <th>spring-cloud-minio</th>
            <th>9012</th>
            <th>图片/文件服务</th>
        </tr>
        <tr>
            <th>3</th>
            <th>spring-cloud-msgpush</th>
            <th>9010</th>
            <th>短信/socket推送服务</th>
        </tr>
         <tr>
                    <th>4</th>
                    <th>spring-cloud-web</th>
                    <th>9011</th>
                    <th>web端API接口</th>
         </tr>
         <tr>
                             <th>5</th>
                             <th>spring-cloud-xxxservice</th>
                             <th>自定义</th>
                             <th>基础服务</th>
         </tr>
</table> 