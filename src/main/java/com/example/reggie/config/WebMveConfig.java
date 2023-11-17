package com.example.reggie.config;

import com.example.reggie.common.JacksonObjectMapper;
import com.example.reggie.common.R;
import com.example.reggie.entity.Employee;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebMveConfig extends WebMvcConfigurationSupport {

    //静态资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/**")
                .addResourceLocations("classpath:/backend/");

        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/");
    }

    //扩展mvc消息转换器
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建新的消息转换器
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        converters.add(0,messageConverter);
    }

}
