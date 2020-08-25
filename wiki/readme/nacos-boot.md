###nacos集中管理所有服务的配置
#####一、单个服务配置
- 添加如下依赖  版本需要对应自己的nacos的版本


    <dependency>
       <groupId>com.alibaba.cloud</groupId>
       <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
    
- 由于该依赖只识别bootstrap.yml文件，所以需要在resource下添加该yml文件,默认后缀是properties
    配置如下信息
  - file-extension需要与nacos管理页面的data-id的后缀一样，name需要与data-id前缀一样
  - 在配置页面配置发布后，本地会在nacos的目录下创建对应的yml文件
  - application.yml文件里面的内容可以只写一些日志的配置
    
    
    spring:
      application:
        name: spring-cloud-gateway
      cloud:
        nacos:
          config:
            file-extension: yaml  

- 需要注意的是在发布配置后，有需要实时值的可以使用@RefreshScope标注
- 但是服务需要重新启动，所以需要统一更改服务后信息后，可单独重启        
            
            
            
            
