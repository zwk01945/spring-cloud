###nacos搭配gateway动态网关
#####一、添加gateway依赖

     <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-gateway</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
            </dependency>
            
#####二、在nacos配置页面中添加DATA_ID
- Data——id为gateway-refresh.json的json数据如下：

        {
      "refreshGatewayRoute":true,
      "routeList":[
        {
          "id":"spring-cloud-msgpush",
          "predicates":[
            {
              "name":"Path",
              "args":{
                "_genkey_0":"/msg/**"
              }
            }
          ],
          "filters":[
    
          ],
          "uri":"lb://spring-cloud-msgpush",
          "order":0
        }
      ]
        }
      
    refreshGatewayRoute代表是否自动更新配置
#####三、创建侦听器

    ConfigService configService = NacosFactory.createConfigService(properties);
     configService.addListener(DATA_ID, GROUP, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }
    
                @Override
                public void receiveConfigInfo(String configInfo) {
                    boolean refreshGatewayRoute = JSONObject.parseObject(configInfo).getBoolean("refreshGatewayRoute");
                    if (refreshGatewayRoute) {
                        List<RouteEntity> list = JSON.parseArray(JSONObject.parseObject(configInfo).getString("routeList")).toJavaList(RouteEntity.class);
                        list.forEach(route ->  update(assembleRouteDefinition(route)));
                    } else {
                        log.info("路由未发生变更");
                    }
                }
            });
            
     //update方法
      public void update(RouteDefinition routeDefinition){
             try {
                 this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
                 log.info("路由移除成功");
             }catch (Exception e){
                 log.error(e.getMessage(), e);
             }
             try {
                 routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                 this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
                 log.info("路由更新成功");
             }catch (Exception e){
                 log.error(e.getMessage(), e);
             }
         }
#####四、实现ApplicationEventPublisherAware

        public RouteDefinitionWriter routeDefinitionWriter;
    
        public ApplicationEventPublisher applicationEventPublisher;
    
        public ConfigService configService;
        @Autowired
        public void setRouteDefinitionWriter(RouteDefinitionWriter routeDefinitionWriter) {
            this.routeDefinitionWriter = routeDefinitionWriter;
        }
    
        @Override
        public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
            this.applicationEventPublisher = applicationEventPublisher;
        }      
#####五、实现CommandLineRunner        
- 到第四步已经实现了配置文件下发动态更新路由
- 实现该接口可以在项目启动后去主动拉取配置中心的配置，方便管理


    log.info("网关启动从nacos拉取网关配置信息.....");
        log.info("data_id,group,timeoutMs:{},{},{}",DATA_ID,GROUP,5000);
        String config = configService.getConfig(DATA_ID, GROUP, 5000);
        System.out.println(config);
        boolean nacosConfig = StringUtils.isEmpty(config);
        if (nacosConfig) {
            log.info("未拉取到服务器配置信息,或者服务器未配置.....");
        } else {
            boolean refreshGatewayRoute = JSONObject.parseObject(config).getBoolean("refreshGatewayRoute");
            if (refreshGatewayRoute) {
                List<RouteEntity> list = JSON.parseArray(JSONObject.parseObject(config).getString("routeList")).toJavaList(RouteEntity.class);
                list.forEach(route ->  update(assembleRouteDefinition(route)));
            } else {
                log.info("服务器未配置路由列表.....");
            }
        }