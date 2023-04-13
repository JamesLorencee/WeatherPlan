package ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model

/*
    date must be in "yyyy-mm-dd" format
    time must be in "hh:mm" format
*/
class Schedule () {
    var id:String? = null
    var title: String? = null
    var location: String? = null
    var event: EventType = EventType.MISCELLANEOUS
    var date: String? = null
    var time: String? = null
    var notes: String? = null

    enum class EventType {
        EVENT,
        MEETING,
        SOCIAL_GATHERING,
        CLASS,
        HOLIDAY,
        MISCELLANEOUS;
    }

    constructor(id: String?, title: String?, location: String?, event: EventType, date:String?, time:String?, notes: String?): this ()


    fun getEnumType (s: String): EventType {
        return when (s.uppercase()) {
            "EVENT" -> EventType.EVENT
            "MEETING" -> EventType.MEETING
            "SOCIAL GATHERING" -> EventType.SOCIAL_GATHERING
            "CLASS" -> EventType.CLASS
            "HOLIDAY" -> EventType.HOLIDAY
            "MISCELLANEOUS" -> EventType.MISCELLANEOUS
            else -> EventType.MISCELLANEOUS
        }
    }
}