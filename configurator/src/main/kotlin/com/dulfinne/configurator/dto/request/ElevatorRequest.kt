package com.dulfinne.configurator.dto.request

import com.dulfinne.configurator.dto.request.configuration.BumpersRequest
import com.dulfinne.configurator.dto.request.configuration.CabinRequest
import com.dulfinne.configurator.dto.request.configuration.CeilingRequest
import com.dulfinne.configurator.dto.request.configuration.ControlPanelRequest
import com.dulfinne.configurator.dto.request.configuration.DoorsRequest
import com.dulfinne.configurator.dto.request.configuration.FloorRequest
import com.dulfinne.configurator.dto.request.configuration.HandrailRequest
import com.dulfinne.configurator.dto.request.configuration.MirrorRequest
import com.dulfinne.configurator.dto.request.configuration.WallRequest

data class ElevatorRequest(
    val cabin: CabinRequest,
    val wall: WallRequest,
    val handrail: HandrailRequest,
    val mirror: MirrorRequest,
    val doors: DoorsRequest,
    val bumpers: BumpersRequest,
    val ceiling: CeilingRequest,
    val floor: FloorRequest,
    val controlPanel: ControlPanelRequest
)
