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


}