1.使用spring cloud的配置中心(远程配置中心为git)
2.使用手动刷新方式从对已经启动的eurekaClient摘取远端配置(使用actuator组件)
3.利用消息队列RabbitMQ自动通知刷新，加上github的webhook就可以做到修改git的远端配置各个项目自动刷新的效果

NOTE: 1.拉取远端配置文件时需要在配置中心服务项目的配置文件中将management.security.enabled配置为false，手动刷新需要发送http://localhost:8763/refresh的post请求
      2.连接rabbitmq时需要在配置文件中将management.security.enabled设置为false，否则会显示无权限，手动发送http://localhost:8763/bus/refresh的post请求模拟webhooks