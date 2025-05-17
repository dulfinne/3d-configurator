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
    INVOICE("invoice", "Накладная"),
    MORTISE("mortise", "Врезная"),
    COMPOSITE("composite", "Прямой"),
    UNIFIED("unified", "Гнутый"),
    HAVE_PORTAL("have_portal", "Есть"),
    NO_PORTAL("no_portal", "Нет"),
    CALL_POST_CASE_1("call_post_case1", "Врезной"),
    CALL_POST_CASE_2("call_post_case2", "Накладной флоренс"),
    CALL_POST_CASE_3("call_post_case3", "Накладной флоренс с табло"),
    CALL_POST_CASE_4("call_post_case4", "Накладной флоренс дуплекс с табло"),
    CALL_POST_CASE_5("call_post_case5", "MOVEL 1 кнопка"),
    CALL_POST_CASE_6("call_post_case6", "MOVEL 2 кнопки"),
    IND_BOARD_CASE_1("ind_board_case1", "Накладное. Вариант 1"),
    IND_BOARD_CASE_2("ind_board_case2", "Врезное. Вариант 1"),
    IND_BOARD_CASE_3("ind_board_case3", "Врезное. Вариант 2"),
    IND_BOARD_CASE_4("ind_board_case4", "Накладное. Вариант 2");

    companion object {
        private val map = entries.associateBy(ReplacementOption::key)
        fun toDisplayName(key: String): String {
            return map[key]?.displayName ?: key
        }
    }
}
