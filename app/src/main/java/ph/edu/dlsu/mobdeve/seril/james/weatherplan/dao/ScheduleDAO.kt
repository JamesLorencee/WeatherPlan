package ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule

interface ScheduleDAO {
    fun addSchedule(schedule: Schedule)
    fun removeSchedule(scheduleId: Int)
    fun getSchedules(): ArrayList<Schedule>
    fun updateSchedule(schedule: Schedule)
}

class ScheduleDAOSQLiteImplementation(var context: Context) : ScheduleDAO{
    override fun addSchedule(schedule: Schedule) {
        TODO("Not yet implemented")
    }

    override fun removeSchedule(scheduleId: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("SimpleDateFormat")
    override fun getSchedules(): ArrayList<Schedule> {
        val scheduleList: ArrayList<Schedule> = ArrayList()
        val selectQuery = "SELECT * FROM ${DatabaseHandler.TABLESCHEDULE}"

        var databaseHandler:DatabaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var cursor: Cursor?

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
                    cursor.getString(4),
                    cursor.getString(5)
                )
                scheduleList.add(schedule)
            } while (cursor.moveToNext())
        }

        db.close()
        return scheduleList
    }

    override fun updateSchedule(schedule: Schedule) {
        TODO("Not yet implemented")
    }

}