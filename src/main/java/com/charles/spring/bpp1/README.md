not eligible for auto-proxying问题排查

# 逻辑关系

1. MyPriorityBeanPostProcess依赖了MyComponent
2. MyBeanPostProcess对MyComponent进行了功能增强
3. MyPriorityBeanPostProcess优先于MyBeanPostProcess加载

# BeanPostProcessor是Bean的前后置处理器

正常来说，所有的BeanPostProcessor都应该在所有的Bean实例化前实例化好，但是，BeanPostProcessor本身也是一个Bean。既然是Bean那就可能会出现依赖其他Bean的可能。基于前面的理论，我们做如下猜想：

1. 假如BeanPostProcessorA依赖了Component1
2. 同时BeanPostProcessorB会在postProcessAfterInitialization中处理Component1。
3. 但是BeanPostProcessorA比BeanPostProcessorB先加载
4. 那么，BeanPostProcessorA实例化时，间接实例化了Component1，可是此时BeanPostProcessorB并没有实例化，就会导致Component1过早实例化，从而错过了某些BeanPostProcessor。

# 当Spring发现这种问题时，就会抛出这样的日志：

```log
Bean 'xxx' of type [xxxxxx] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
# 当前例子
Bean 'myComponent' of type [com.charles.spring.bpp1.DataHolder] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
```

# SpringBoot Bean加载优先级问题

## 同一个类中加载顺序

Constructor > @Autowired > @PostConstruct > @Bean

## @DependsOn控制顺序

如果A不依赖B，但是A需要在B后面初始化，可以使用@DependsOn(value="Bbeanname")。B的@Bean上面需要手动指定Name,否则找不到。

## @Order不能控制顺序

@Order注解并不能改变Bean加载优先级，@Order注解用于设置装载到list中Bean的顺序
