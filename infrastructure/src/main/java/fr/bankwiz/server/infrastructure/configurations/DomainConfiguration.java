package fr.bankwiz.server.infrastructure.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import ddd.DomainService;

@Configuration
@ComponentScan(
        basePackages = {"fr.bankwiz.server.domain"},
        includeFilters = {
            @ComponentScan.Filter(
                    type = FilterType.ANNOTATION,
                    classes = {DomainService.class})
        })
public class DomainConfiguration {}
