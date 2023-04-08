package ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule

interface ScheduleDAO {
    fun addSchedule(schedule: Schedule)
    fun removeSchedule(scheduleId: Int)
    fun getSchedules(): ArrayList<Schedule>
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

    override fun addSchedule(schedule: Schedule) {
        firebase = Firebase.database
        val scheduleRoot = firebase.reference.child("root").child("schedule")
        println(schedule.title)

        scheduleRoot.push().setValue(schedule)
    }

    override fun removeSchedule(scheduleId: Int) {
        TODO("Not yet implemented")
    }

    override fun getSchedules(): ArrayList<Schedule> {
        var result = ArrayList<Schedule>()
        firebase = Firebase.database

        readData(object : ScheduleCallback { override fun onCallback(scheduleList: ArrayList<Schedule>) {
            result = scheduleList
            println("Inside: " + result.size)
        }})
        return result
    }

    override fun updateSchedule(schedule: Schedule) {
        TODO("Not yet implemented")
    }

    private fun readData (scheduleCallback: ScheduleCallback) {
        var scheduleList = ArrayList<Schedule>()

        Firebase.database.reference.child("root").child("schedule").get().addOnSuccessListener {
            val schedules = it.children

            for (schedule in schedules) {
                var data = schedule.getValue(Schedule::class.java)!!
                var newSchedule = Schedule()
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
        }.addOnCanceledListener {
            Log.e("ERROR", "UNABLE TO RETRIEVE DATA")
        }
    }

    private interface ScheduleCallback {
        fun onCallback (scheduleList: ArrayList<Schedule>)
    }

}