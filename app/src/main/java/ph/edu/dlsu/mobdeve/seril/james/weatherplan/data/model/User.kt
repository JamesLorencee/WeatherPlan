package ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model

class User () {
    var uid: String? = null
    var email: String? = null
    var username: String? = null
    var scheduleList: ArrayList<Schedule> = ArrayList<Schedule>()

    constructor(uid: String?, email: String?, username: String?, scheduleList: ArrayList<Schedule>): this()
}