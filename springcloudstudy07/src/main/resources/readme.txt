1.使用Http客户端组件Feign

NOTE: FeignClient中请求的api如果有参数需要传递，需要在参数处使用@RequestParam注解，否则请求的api认为是非法请求，会报405错误
      详见com.clo.scs.service.ApiService中hello方法