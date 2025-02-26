package kr.bit.config;

import kr.bit.aspect.LoggingAspect;
import kr.bit.service.LogService;
import org.springframework.context.annotation.*;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;


@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true) // AOP 활성화
@ComponentScan(basePackages = "kr.bit")
public class AopConfig {

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true); // JDK 동적 프록시 사용 (인터페이스 기반)
        return creator;
    }
}