package com.dulfinne.configurator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class ConfiguratorApplication

fun main(args: Array<String>) {
    runApplication<ConfiguratorApplication>(*args)
}
