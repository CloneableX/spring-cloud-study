1.使用熔断器组件Hystrix，由于Feign组件内置了Hystrix，只要在配置文件中启用就行
2.使用Hystrix的监控仪表盘

NOTE: 1.Hystrix使用单元测试永远访问不到服务
      2.由于熔断器回调类实现了FeignClient接口，注入FeignClient的ApiService时要使用Resource注解