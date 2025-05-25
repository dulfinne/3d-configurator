package com.dulfinne.configurator.controller

import com.dulfinne.configurator.dto.request.LightSettingsRequest
import com.dulfinne.configurator.dto.response.LightSettingsResponse
import com.dulfinne.configurator.service.LightSettingsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/light-settings")
@CrossOrigin
class LightSettingsController(val service: LightSettingsService) {

    @GetMapping
    fun getLightSettings(): LightSettingsResponse {
        val response = service.getLightSettings()
        return response
    }

    @PostMapping
    fun saveLightSettings(@RequestBody request: LightSettingsRequest): ResponseEntity<LightSettingsResponse> {
        val response = service.saveLightSettings(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PutMapping
    fun updateLightSettings(@RequestBody request: LightSettingsRequest): LightSettingsResponse {
        val response = service.updateLightSettings(request)
        return response
    }
}