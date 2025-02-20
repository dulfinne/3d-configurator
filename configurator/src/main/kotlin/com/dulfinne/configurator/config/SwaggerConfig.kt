package com.dulfinne.configurator.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info


@OpenAPIDefinition(
    info = Info(
        title = "3d-configurator Api",
        description = "API to configure elevator design",
        version = "1.0.0",
        contact = Contact(name = "Zhukava Yana", email = "yanazhukava@gmail.com")
    )
)
class SwaggerConfig