package com.alpsakaci.listflit.infrastructure.configuration.swagger

import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("list-flit")
            .packagesToScan("com.alpsakaci.listflit.infrastructure.controller")
            .build()
    }
}