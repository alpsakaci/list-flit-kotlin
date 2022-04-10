package com.alpsakaci.listflit.infrastructure.configuration.feign

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(
    value = ["com.alpsakaci.listflit.infrastructure.httpclient"]
)
class FeignConfiguration {
    // TODO: Impl
}
