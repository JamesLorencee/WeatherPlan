package ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

/*
    date must be in "yyyy-mm-dd" format
    time must be in "hh:mm" format
*/
class Schedule (var id: Int, var title: String, var location: String?, var event: EventType, date:String, time:String, var notes: String?) {
    var datetime: Date = getDatetime(date, time)

    enum class EventType {
        EVENT,
        MEETING,
        SOCIAL_GATHERING,
        CLASS,
        HOLIDAY,
        MISCELLANEOUS;
    }

    constructor() : this (999, "", null, EventType.MISCELLANEOUS, "1970-01-01", "00:00", null)

    @SuppressLint("SimpleDateFormat")
    private fun getDatetime (date:String, time:String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return format.parse("$date $time") as Date
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateString (): String {
        return SimpleDateFormat("MMMM dd, yyyy").format(datetime)
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeString(): String {
        return SimpleDateFormat("hh:mm a").format(datetime)
    }
    fun getEnumType (s: String): EventType {
        return when (s.uppercase()) {
            "EVENT" -> EventType.EVENT
            "MEETING" -> EventType.MEETING
            "SOCIAL_GATHERING" -> EventType.SOCIAL_GATHERING
            "CLASS" -> EventType.CLASS
            "HOLIDAY" -> EventType.HOLIDAY
            "MISCELLANEOUS" -> EventType.MISCELLANEOUS
            else -> EventType.MISCELLANEOUS
        }
    }
}