package ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule

interface ScheduleListener {
    fun onSchedulesReceived(scheduleList: ArrayList<Schedule>)
}

interface ScheduleDAO {
    fun addSchedule(schedule: Schedule)
    fun removeSchedule(scheduleId: String)
    fun getSchedules(listener: ScheduleListener)
    fun updateSchedule(schedule: Schedule)
}

//class ScheduleDAOSQLiteImplementation(var context: Context) : ScheduleDAO{
//    @SuppressLint("SimpleDateFormat")
//    override fun addSchedule(schedule: Schedule) {
//        val databaseHandler = DatabaseHandler(context)
//        val db = databaseHandler.writableDatabase
//
//        val contentValues = ContentValues()
//        contentValues.put(DatabaseHandler.TABLESCHEDULETITLE, schedule.title)
//        contentValues.put(DatabaseHandler.TABLESCHEDULELOCATION, schedule.location)
//        contentValues.put(DatabaseHandler.TABLESCHEDULEEVENTTYPE, schedule.event.toString())
//        contentValues.put(DatabaseHandler.TABLESCHEDULEDATE, SimpleDateFormat("yyyy-MM-dd").format(schedule.datetime!!))
//        contentValues.put(DatabaseHandler.TABLESCHEDULETIME, SimpleDateFormat("hh:mm").format(schedule.datetime!!))
//        contentValues.put(DatabaseHandler.TABLESCHEDULENOTES, schedule.notes)
//
//        db.insert(DatabaseHandler.TABLESCHEDULE, null, contentValues)
//        db.close()
//    }
//
//    override fun removeSchedule(scheduleId: Int) {
//        val databaseHandler = DatabaseHandler(context)
//        val db = databaseHandler.writableDatabase
//
//        db.delete(DatabaseHandler.TABLESCHEDULE,
//        "${DatabaseHandler.TABLESCHEDULEID}=$scheduleId",
//        null)
//
//        db.close()
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    override fun getSchedules(): ArrayList<Schedule> {
//        val scheduleList: ArrayList<Schedule> = ArrayList()
//        val selectQuery = "SELECT * FROM ${DatabaseHandler.TABLESCHEDULE}"
//
//        val databaseHandler = DatabaseHandler(context)
//        val db = databaseHandler.readableDatabase
//        val cursor: Cursor?
//
//        try{
//            cursor = db.rawQuery(selectQuery, null)
//        } catch (e: SQLiteException) {
//            db.close()
//            return ArrayList()
//        }
//
//        if (cursor.moveToFirst()) {
//            do {
//                val schedule = Schedule(
//                    cursor.getInt(0),
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    Schedule().getEnumType(cursor.getString(3)),
//                    cursor.getString(4),
//                    cursor.getString(5),
//                    cursor.getString(6)
//                )
//                scheduleList.add(schedule)
//            } while (cursor.moveToNext())
//        }
//
//        cursor.close()
//        db.close()
//        return scheduleList
//    }
//
//    @SuppressLint("SimpleDateFormat")
//    override fun updateSchedule(schedule: Schedule) {
//        val databaseHandler = DatabaseHandler(context)
//        val db = databaseHandler.writableDatabase
//
//        val contentValues = ContentValues()
//        contentValues.put(DatabaseHandler.TABLESCHEDULETITLE, schedule.title)
//        contentValues.put(DatabaseHandler.TABLESCHEDULELOCATION, schedule.location)
//        contentValues.put(DatabaseHandler.TABLESCHEDULEEVENTTYPE, schedule.event.toString())
//        contentValues.put(DatabaseHandler.TABLESCHEDULEDATE, SimpleDateFormat("yyyy-MM-dd").format(schedule.datetime!!))
//        contentValues.put(DatabaseHandler.TABLESCHEDULETIME, SimpleDateFormat("hh:mm").format(schedule.datetime!!))
//        contentValues.put(DatabaseHandler.TABLESCHEDULENOTES, schedule.notes)
//
//        db.update(
//            DatabaseHandler.TABLESCHEDULE,
//            contentValues,
//            "${DatabaseHandler.TABLESCHEDULEID}=${schedule.id}",
//            null
//        )
//
//        db.close()
//    }
//
//}

class ScheduleDAOFFirebaseImplementation : ScheduleDAO{

    private lateinit var firebase: FirebaseDatabase
    private var auth = FirebaseAuth.getInstance()

    override fun addSchedule(schedule: Schedule) {
        firebase = Firebase.database
        val scheduleRoot = Firebase.database.reference.child("root").child("users").child(auth.currentUser!!.uid).child("scheduleList")

        scheduleRoot.child(schedule.id!!).setValue(schedule)
    }

    override fun removeSchedule(scheduleId: String) {
        firebase = Firebase.database
        val scheduleRoot = Firebase.database.reference.child("root").child("users").child(auth.currentUser!!.uid).child("scheduleList")

        scheduleRoot.child(scheduleId).removeValue()
    }

    override fun getSchedules(listener: ScheduleListener) {
        firebase = Firebase.database

        readData(object : ScheduleCallback {
            override fun onCallback(scheduleList: ArrayList<Schedule>) {
                listener.onSchedulesReceived(scheduleList)
        }})
    }

    override fun updateSchedule(schedule: Schedule) {
        firebase = Firebase.database
        val scheduleRoot = Firebase.database.reference.child("root").child("users").child(auth.currentUser!!.uid).child("scheduleList").child(schedule.id.toString())

        scheduleRoot.child("title").setValue(schedule.title)
        scheduleRoot.child("location").setValue(schedule.location)
        scheduleRoot.child("event").setValue(schedule.event)
        scheduleRoot.child("date").setValue(schedule.date)
        scheduleRoot.child("time").setValue(schedule.time)
        scheduleRoot.child("notes").setValue(schedule.notes)
    }

    private fun readData (scheduleCallback: ScheduleCallback) {
        val scheduleList = ArrayList<Schedule>()
        val scheduleRoot = Firebase.database.reference.child("root").child("users").child(auth.currentUser!!.uid).child("scheduleList")

        scheduleRoot.get().addOnSuccessListener {
            val schedules = it.children

            for (schedule in schedules) {
                val data = schedule.getValue(Schedule::class.java)!!
                val newSchedule = Schedule()
                newSchedule.id = data.id
                newSchedule.title = data.title
                newSchedule.location = data.location
                newSchedule.event = data.event
                newSchedule.date = data.date
                newSchedule.time = data.time
                newSchedule.notes = data.notes
                scheduleList.add(newSchedule)
            }
            scheduleCallback.onCallback(scheduleList)
        }.addOnFailureListener {
            Log.e("ERROR", "UNABLE TO RETRIEVE DATA")
        }
    }

    private interface ScheduleCallback {
        fun onCallback (scheduleList: ArrayList<Schedule>)
    }

}