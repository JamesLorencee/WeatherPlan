package ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION) {
    companion object {
        private val DATABASEVERSION = 1
        private val DATABASENAME = "ScheduleDatabase"

        const val TABLESCHEDULE = "schedule_table"
        const val TABLESCHEDULEID = "schedule_id"
        const val TABLESCHEDULETITLE = "schedule_title"
        const val TABLESCHEDULELOCATION = "schedule_location"
        const val TABLESCHEDULEEVENTTYPE = "schedule_event_type"
        const val TABLESCHEDULEDATE = "schedule_date"
        const val TABLESCHEDULETIME = "schedule_time"
        const val TABLESCHEDULENOTES = "schedule_notes"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATEACCOUNTSTABLE =
            "CREATE TABLE $TABLESCHEDULE " +
                    "($TABLESCHEDULEID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$TABLESCHEDULETITLE TEXT, " +
                    "$TABLESCHEDULELOCATION TEXT, " +
                    "$TABLESCHEDULEEVENTTYPE TEXT, " +
                    "$TABLESCHEDULEDATE DATE, " +
                    "$TABLESCHEDULETIME TIME, " +
                    "$TABLESCHEDULENOTES LONGTEXT)"
        db?.execSQL(CREATEACCOUNTSTABLE)

        db?.execSQL("Insert into $TABLESCHEDULE " +
                "($TABLESCHEDULETITLE, $TABLESCHEDULELOCATION, $TABLESCHEDULEEVENTTYPE, $TABLESCHEDULEDATE, $TABLESCHEDULETIME, $TABLESCHEDULENOTES)" +
                "values ('MOBDEVE', 'GK304B', 'CLASS', '2023-03-31', '16:15', 'MOBDEVE class with Sir Marco')")
        db?.execSQL("Insert into $TABLESCHEDULE " +
                "($TABLESCHEDULETITLE, $TABLESCHEDULELOCATION, $TABLESCHEDULEEVENTTYPE, $TABLESCHEDULEDATE, $TABLESCHEDULETIME, $TABLESCHEDULENOTES)" +
                "values ('Sample Title', 'Sample Location', 'EVENT', '1970-01-01', '10:00', 'Sample Notes')")
        db?.execSQL("Insert into $TABLESCHEDULE " +
                "($TABLESCHEDULETITLE, $TABLESCHEDULELOCATION, $TABLESCHEDULEEVENTTYPE, $TABLESCHEDULEDATE, $TABLESCHEDULETIME, $TABLESCHEDULENOTES)" +
                "values ('Birthday Party', 'BGC', 'SOCIAL_GATHERING', '2023-03-31', '22:00', 'Notes')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLESCHEDULE")
        onCreate(db)
    }
}