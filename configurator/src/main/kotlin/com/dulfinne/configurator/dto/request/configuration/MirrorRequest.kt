package com.dulfinne.configurator.dto.request.configuration

data class MirrorRequest(
    val existence: String,
    val back: Boolean,
    val left: Boolean,
    val right: Boolean,
    val type: String
)
