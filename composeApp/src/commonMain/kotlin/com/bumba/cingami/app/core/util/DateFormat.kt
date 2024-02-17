package com.bumba.cingami.app.core.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private fun Int.toTwoNumbers(): String {
    return if (this <= 9)
        "0$this"
    else this.toString()
}

fun LocalDateTime.toFormattedDate(): String {
    return "${dayOfMonth.toTwoNumbers()}/${monthNumber.toTwoNumbers()}/$year " +
            "${hour.toTwoNumbers()}:${minute.toTwoNumbers()}:${second.toTwoNumbers()}"
}

fun Long.toFormattedData(): String {
    val localDateTime = Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
    return localDateTime.toFormattedDate()
}