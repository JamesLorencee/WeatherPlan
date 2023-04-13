package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleDAOFFirebaseImplementation
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleListener
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.ScheduleAdapter
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityEditScheduleBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditScheduleActivity : AppCompatActivity(), ScheduleListener {

    private lateinit var binding: ActivityEditScheduleBinding
    private lateinit var scheduleAdapter: ScheduleAdapter

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scheduleDAO = ScheduleDAOFFirebaseImplementation()
        scheduleDAO.getSchedules(this)

        // access the items of the list
        val event = resources.getStringArray(R.array.EventType)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.edit_eventType_spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, event
            )
            spinner.adapter = adapter

            val selection = adapter.getPosition(intent.getStringExtra("eventtype"))
            spinner.setSelection(selection)
        }

        val datetime = SimpleDateFormat("yyyy-MM-dd HH:mm")
            .parse("${intent.getStringExtra("date").toString()} ${intent.getStringExtra("time").toString()}")

        binding.editTimePicker.setIs24HourView(true)
        binding.editTimePicker.hour = SimpleDateFormat("HH").format(datetime!!).toInt()
        binding.editTimePicker.minute = SimpleDateFormat("mm").format(datetime).toInt()

        binding.datePicker.updateDate(
            SimpleDateFormat("yyyy").format(datetime).toInt(),
            SimpleDateFormat("MM").format(datetime).toInt() - 1,
            SimpleDateFormat("dd").format(datetime).toInt()
            )

        binding.etEditTitle.setText(intent.getStringExtra("title"))
        binding.etEditLocation.setText(intent.getStringExtra("location"))
        binding.etEditNotes.setText(intent.getStringExtra("notes"))

        binding.editSchedSaveBtn.setOnClickListener {
            if (binding.etEditTitle.text.isNotEmpty()
                && binding.etEditLocation.text.isNotEmpty()
            ) {
                val editIntent = Intent()

                val calendar: Calendar = Calendar.getInstance()
                calendar.set(binding.datePicker.year, binding.datePicker.month, binding.datePicker.dayOfMonth, binding.editTimePicker.hour, binding.editTimePicker.minute)

                val schedule = Schedule()
                schedule.id = intent.getStringExtra("id")
                schedule.title = binding.etEditTitle.text.toString()
                schedule.location = binding.etEditLocation.text.toString()
                schedule.event = Schedule().getEnumType(binding.editEventTypeSpinner.selectedItem.toString())
                schedule.date = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
                schedule.time = SimpleDateFormat("HH:mm").format(calendar.time)
                schedule.notes = binding.etEditNotes.text.toString()

                scheduleAdapter.editSchedule(schedule, intent.getIntExtra("position", -999))

                editIntent.putExtra("id", intent.getStringExtra("id"))
                editIntent.putExtra("title", schedule.title)
                editIntent.putExtra("location", schedule.location)
                editIntent.putExtra("eventtype", schedule.event.toString())
                editIntent.putExtra("time", schedule.time)
                editIntent.putExtra("date", schedule.date)
                editIntent.putExtra("notes", schedule.notes)
                editIntent.putExtra("position", intent.getIntExtra("position", -999))
                setResult(1, editIntent)
                finish()
            }
        }

        binding.editSchedCancelBtn.setOnClickListener {
            val cancelIntent = Intent()
            cancelIntent.putExtra("id", intent.getStringExtra("id"))
            cancelIntent.putExtra("title", intent.getStringExtra("title"))
            cancelIntent.putExtra("location", intent.getStringExtra("location"))
            cancelIntent.putExtra("eventtype", intent.getStringExtra("eventtype"))
            cancelIntent.putExtra("time", intent.getStringExtra("time"))
            cancelIntent.putExtra("date", intent.getStringExtra("date"))
            cancelIntent.putExtra("notes", intent.getStringExtra("notes"))
            cancelIntent.putExtra("position", intent.getIntExtra("position", -999))
            setResult(0, cancelIntent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        println(intent.getStringExtra("title"))
    }

    override fun onSchedulesReceived(scheduleList: ArrayList<Schedule>) {
        scheduleAdapter = ScheduleAdapter(this, scheduleList)
    }
}