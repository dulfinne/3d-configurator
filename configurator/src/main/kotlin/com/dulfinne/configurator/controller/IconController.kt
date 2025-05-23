package com.dulfinne.configurator.controller

import com.dulfinne.configurator.controller.api.IconApi
import com.dulfinne.configurator.dto.response.IconResponse
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.service.IconService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/icons")
@CrossOrigin
class IconController(val iconService: IconService) : IconApi {

    @GetMapping
    override fun getAllIcons(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "6") size: Int
    ): PaginatedResponse<IconResponse> {
        val iconResponsePage = iconService.getAllIcons(page, size)
        return iconResponsePage;
    }
}