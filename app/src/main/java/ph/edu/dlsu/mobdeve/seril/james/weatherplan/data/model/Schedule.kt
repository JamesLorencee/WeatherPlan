package ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

/*
    date must be in "yyyy-mm-dd" format
    time must be in "hh:mm" format
*/
class Schedule (var title: String, var location: String, var event: EventType, var notes: String, date:String, time:String) {
    private var datetime: Date = getDatetime(date, time)

    enum class EventType {
        EVENT,
        MEETING,
        SOCIAL_GATHERING,
        CLASS,
        HOLIDAY,
        MISCELLANEOUS
    }

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
}