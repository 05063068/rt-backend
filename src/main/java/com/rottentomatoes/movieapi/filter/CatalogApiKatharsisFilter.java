package com.rottentomatoes.movieapi.filter;

import io.katharsis.invoker.KatharsisInvokerBuilder;
import io.katharsis.locator.JsonServiceLocator;
import io.katharsis.servlet.SampleKatharsisFilter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CatalogApiKatharsisFilter extends SampleKatharsisFilter implements BeanFactoryAware {

    private static final String DEFAULT_RESOURCE_SEARCH_PACKAGE = "com.rottentomatoes.movieapi.domain";

    private static final String RESOURCE_DEFAULT_DOMAIN = "http://localhost:8080";

    private BeanFactory beanFactory;

    @Override
    public String getResourceSearchPackage() {
        String resourceSearchPackage = super.getResourceSearchPackage();

        if (StringUtils.isEmpty(resourceSearchPackage)) {
            resourceSearchPackage = DEFAULT_RESOURCE_SEARCH_PACKAGE;
        }

        return resourceSearchPackage;
    }

    @Override
    public String getResourceDefaultDomain() {
        String resourceDefaultDomain = super.getResourceDefaultDomain();

        if (StringUtils.isEmpty(resourceDefaultDomain)) {
            resourceDefaultDomain = RESOURCE_DEFAULT_DOMAIN;
        }

        return resourceDefaultDomain;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    protected KatharsisInvokerBuilder createKatharsisInvokerBuilder() {
        KatharsisInvokerBuilder builder = new CustomKatharsisInvokerBuilder();

        builder.resourceSearchPackage(getResourceSearchPackage())
            .resourceDefaultDomain(getResourceDefaultDomain())
            .jsonServiceLocator(new JsonServiceLocator() {

                @Override
                public <T> T getInstance(Class<T> clazz) {
                    // Simply retrieve a bean by the repository class type.
                    return beanFactory.getBean(clazz);
                }

            });

        return builder;
    }

}
