package backtobasicsoop.domain.entities

import java.time.LocalDateTime

data class TimeOfDay(val hours: Int, val minutes: Int) {
    init {
        if (hours < 0 || hours > 23) {
            throw IllegalArgumentException("Hours should be between 0 and 23.")
        }
        if (minutes < 0 || minutes > 59) {
            throw IllegalArgumentException("Minutes should be between 0 and 59")
        }
    }

    operator fun compareTo(other: TimeOfDay) =
        if (hours != other.hours) hours.compareTo(other.hours) else minutes.compareTo(other.minutes)

    companion object {
        val now: TimeOfDay
            get() {
                val dateTimeNow = LocalDateTime.now()
                return TimeOfDay(dateTimeNow.hour, dateTimeNow.hour)
            }
    }
}