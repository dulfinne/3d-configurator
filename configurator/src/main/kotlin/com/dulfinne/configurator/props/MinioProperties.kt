package com.dulfinne.configurator.props

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "minio")
class MinioProperties {
    lateinit var url: String
    lateinit var publicUrl: String
    lateinit var accessKey: String
    lateinit var secretKey: String
}