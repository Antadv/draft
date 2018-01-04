package com.somelogs.spring;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * 获取由 Spring 加载的配置文件属性
 *
 * @author LBG - 2017/11/28 0028
 */
@Component
public class SpringPropertiesUtil implements EmbeddedValueResolverAware {

    private StringValueResolver stringValueResolver;

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
    }

    /**
     * 根据key获取属性：key为 ${key}格式
     * @param key
     * @return
     */
    public String getStringValue(String key) {
        return stringValueResolver.resolveStringValue(key);
    }
}
