package com.dulfinne.configurator.entity.enums

enum class IconType(val id: Int) {
    UNKNOWN(0),
    DOOR(1),
    WALL(2),
    FLOOR(3),
    CEILING(4),
    CEILING_MATERIAL(5),
    CONTROL_PANEL(6),
    HANDRAIL(7),
    BUMPER(8),
    INDICATION_BOARD(9);

    companion object {
        fun fromId(id: Int): IconType {
            return entries.find { it.id == id }
                ?: UNKNOWN
        }
    }
}