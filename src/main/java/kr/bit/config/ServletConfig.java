package kr.bit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"kr.bit.controller", "kr.bit.service", "kr.bit.dao", "kr.bit.mapper"})
public class ServletConfig implements WebMvcConfigurer {

    @Bean
    public ViewResolver thymeleafViewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10485760); // 10MB
        return multipartResolver;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(
                        "file:C:/Mvc_Project_Manager/src/main/resources/static/images/",
                        "classpath:/static/images/"
                );

        registry.addResourceHandler("/controller/images/**")
                .addResourceLocations(
                        "file:C:/Mvc_Project_Manager/src/main/resources/static/images/",
                        "classpath:/static/images/"
                );

        registry.addResourceHandler("/css/**", "/controller/css/**")
                .addResourceLocations(
                        "file:C:/Mvc_Project_Manager/src/main/resources/static/css/",
                        "classpath:/static/css/"
                );

        registry.addResourceHandler("/resources/**", "/controller/resources/**")
                .addResourceLocations("/resources/");

        registry.addResourceHandler("/static/**", "/controller/static/**")
                .addResourceLocations("classpath:/static/");
    }
    }
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(20971520);  // 최대 업로드 크기 20MB
        resolver.setMaxUploadSizePerFile(41943040); // 한번에 업로드 가능한 최대 크기 40MB
        resolver.setMaxInMemorySize(20971520); // 메모리 임계값
        return resolver;
    }


