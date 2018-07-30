package com.common.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.Set;

/**
 * 结合shiro简单扩展thymeleaf javascript处理器，支持对javascrip脚本的权限控制
 **/
public class ShiroXDialect extends ShiroDialect {

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = super.getProcessors(dialectPrefix);
        processors.add(new HasPermissionJavascriptProcessor());
        return processors;
    }
}
