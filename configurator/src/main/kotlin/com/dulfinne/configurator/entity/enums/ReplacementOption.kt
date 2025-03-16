package com.dulfinne.configurator.entity.enums

enum class ReplacementOption(val key: String, val displayName: String) {

    WIDE_SIZE("wideSize", "Широкая"),
    SQUARE_SIZE("squareSize", "Квадратая"),
    DEEP_SIZE("deepSize", "Глубокая"),
    NOT_WALK_THROUGH_CABIN("not_walk_through_cabin", "Непроходная"),
    WALK_THROUGH_CABIN("walk_through_cabin", "Проходная"),
    CENTRAL_OPEN_TYPE("centralOpenType", "Центральное"),
    LEFT_OPEN_TYPE("leftOpenType", "Телескопическое (левое)"),
    RIGHT_OPEN_TYPE("rightOpenType", "Телескопическое (правое)"),
    HORIZONTAL("horizontal", "Горизонтальная"),
    VERTICAL("vertical", "Вертикальная"),
    LEFT_PANEL_SIDE("leftPanelSide", "Левая"),
    RIGHT_PANEL_SIDE("rightPanelSide", "Правая"),
    CLOSER_TO_DOOR_PANEL_POSITION("closerToDoorPanelPosition", "Ближе к двери"),
    CENTER_PANEL_POSITION("centerPanelPosition", "По центру"),
    BOTH_SIDES_PANEL_POSITION("bothSidesPanelPosition", "С двух сторон"),
    FRONT_WALL_POSITION("frontWallPosition", "Ближе к передней стене"),
    BACK_WALL_POSITION("backWallPosition", "Ближе к задней стене"),
    HAVE_MIRROR("haveMirror", "Да"),
    NO_MIRROR("noMirror", "Нет"),
    FLOOR("floor", "В пол"),
    TO_RAIL("to_rail", "До поручня"),
    BACK_MIRROR("backMirror", "Сзади"),
    RIGHT_MIRROR("rightMirror", "Справа"),
    LEFT_MIRROR("leftMirror", "Слева"),
    HAVE_HAND("haveHand", "Да"),
    NO_HAND("noHand", "Нет"),
    COMPOSITE("composite", "Составной"),
    UNIFIED("unified", "Объединённый");

    companion object {
        private val map = entries.associateBy(ReplacementOption::key)
        fun toDisplayName(key: String): String {
            return map[key]?.displayName ?: key
        }
    }
}
