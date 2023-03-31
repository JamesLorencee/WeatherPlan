package ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import java.text.SimpleDateFormat

interface ScheduleDAO {
    fun addSchedule(schedule: Schedule)
    fun removeSchedule(scheduleId: Int)
    fun getSchedules(): ArrayList<Schedule>
    fun updateSchedule(schedule: Schedule)
}

class ScheduleDAOSQLiteImplementation(var context: Context) : ScheduleDAO{
    @SuppressLint("SimpleDateFormat")
    override fun addSchedule(schedule: Schedule) {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.TABLESCHEDULETITLE, schedule.title)
        contentValues.put(DatabaseHandler.TABLESCHEDULELOCATION, schedule.location)
        contentValues.put(DatabaseHandler.TABLESCHEDULEEVENTTYPE, schedule.event.toString())
        contentValues.put(DatabaseHandler.TABLESCHEDULEDATE, SimpleDateFormat("yyyy-MM-dd").format(schedule.datetime))
        contentValues.put(DatabaseHandler.TABLESCHEDULETIME, SimpleDateFormat("hh:mm").format(schedule.datetime))
        contentValues.put(DatabaseHandler.TABLESCHEDULENOTES, schedule.notes)

        db.insert(DatabaseHandler.TABLESCHEDULE, null, contentValues)
        db.close()
    }

    override fun removeSchedule(scheduleId: Int) {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        db.delete(DatabaseHandler.TABLESCHEDULE,
        "${DatabaseHandler.TABLESCHEDULEID}=$scheduleId",
        null)

        db.close()
    }

    @SuppressLint("SimpleDateFormat")
    override fun getSchedules(): ArrayList<Schedule> {
        val scheduleList: ArrayList<Schedule> = ArrayList()
        val selectQuery = "SELECT * FROM ${DatabaseHandler.TABLESCHEDULE}"

        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.close()
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                val schedule = Schedule(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    Schedule().getEnumType(cursor.getString(3)),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                )
                scheduleList.add(schedule)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return scheduleList
    }

    @SuppressLint("SimpleDateFormat")
    override fun updateSchedule(schedule: Schedule) {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.TABLESCHEDULETITLE, schedule.title)
        contentValues.put(DatabaseHandler.TABLESCHEDULELOCATION, schedule.location)
        contentValues.put(DatabaseHandler.TABLESCHEDULEEVENTTYPE, schedule.event.toString())
        contentValues.put(DatabaseHandler.TABLESCHEDULEDATE, SimpleDateFormat("yyyy-MM-dd").format(schedule.datetime))
        contentValues.put(DatabaseHandler.TABLESCHEDULETIME, SimpleDateFormat("hh:mm").format(schedule.datetime))
        contentValues.put(DatabaseHandler.TABLESCHEDULENOTES, schedule.notes)

        db.update(
            DatabaseHandler.TABLESCHEDULE,
            contentValues,
            "${DatabaseHandler.TABLESCHEDULEID}=${schedule.id}",
            null
        )

        db.close()
    }

}