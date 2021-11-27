package com.example.audino.utils

object Constants {

    const val ROOT_ID = "AUDINO_ROOT_ID"

    //notification
    const val NOTIFICATION_ID = 1
    const val CHANNEL_ID = "AudinoChannel"
    const val CHANNEL_NAME = "AudinoChannelName"

    //local broadcast actions
    const val ACTION_PLAYER_PLAYING_STATE_CHANGED = "ACTION_PLAYER_PLAYING_STATE_CHANGED"
    const val ACTION_SEND_PENDING_BROADCAST = "SEND_PENDING_BROADCAST"
    const val ACTION_SCHEDULE_SLEEP_TIMER = "ACTION_SCHEDULE_SLEEP_TIMER"

    //time constants
    const val minToMillis = 60*1000L

    const val NO_TIME = 0 * minToMillis
    const val MIN_15 = 15 * minToMillis
    const val MIN_30 = 30 * minToMillis
    const val MIN_45 = 45 * minToMillis

    const val SHARED_PREFS_NAME = "Audino_shared_pref"
    const val PREFERRED_TEXT_SIZE = "pref_text_size"
    const val PREFERRED_BG_COLOR = "pref_bg_color"

    const val TEXT_SIZE_0 = 0 //small
    const val TEXT_SIZE_1 = 1 //medium
    const val TEXT_SIZE_2 = 2 //large

    const val BG_LIGHT = 0 // light
    const val BG_DARK = 1 // dark

    const val SHARE_BOOK_TEMPLATE = """Hey there, read this awesome book titled "{{BOOK_NAME}}" by "{{AUTHOR}}" on Audino!
        https://audino.com/{{BOOK_ID}}"""

}