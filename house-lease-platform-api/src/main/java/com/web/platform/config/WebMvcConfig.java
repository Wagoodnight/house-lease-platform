package com.web.platform.config;

import com.web.platform.common.Const;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author YaeMiko
 */
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置静态资源处理
     * @param registry 注册
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = converters.stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .findFirst()
                .map(converter -> (MappingJackson2HttpMessageConverter) converter)
                .orElseGet(MappingJackson2HttpMessageConverter::new);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(Const.DATE_TIME_FORMATTER));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(Const.DATE_FORMATTER));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(Const.TIME_FORMATTER));
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(Const.DATE_TIME_FORMATTER));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(Const.DATE_FORMATTER));
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(Const.TIME_FORMATTER));
        objectMapper.registerModule(timeModule);
        messageConverter.setObjectMapper(objectMapper);
        if(!converters.contains(messageConverter)) {
            converters.add(messageConverter);
        }
    }
}
