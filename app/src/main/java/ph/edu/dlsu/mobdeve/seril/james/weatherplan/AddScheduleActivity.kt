package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleDAOFFirebaseImplementation
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.ScheduleAdapter
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityAddScheduleBinding
import java.text.SimpleDateFormat
import java.util.*

class AddScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddScheduleBinding

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // access the items of the list
        val event = resources.getStringArray(R.array.EventType)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.eventType_spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, event
            )
            spinner.adapter = adapter
        }

        binding.addTimePicker.setIs24HourView(true)

        // If User Canceled the add sched, it will return to the home activity
        binding.addSchedCancelBtn.setOnClickListener{
            val goToHome = Intent(this, HomeActivity::class.java)
            startActivity(goToHome)
        }

        binding.addSchedSaveBtn.setOnClickListener{
            if (binding.etAddTitle.text.isNotEmpty()
                && binding.etAddLocation.text.isNotEmpty()
            ) {
                val calendar: Calendar = Calendar.getInstance()
                calendar.set(binding.datePicker.year, binding.datePicker.month, binding.datePicker.dayOfMonth, binding.addTimePicker.hour, binding.addTimePicker.minute)

                val schedule = Schedule()
                schedule.title = binding.etAddTitle.text.toString()
                schedule.location = binding.etAddLocation.text.toString()
                schedule.event = Schedule().getEnumType(binding.eventTypeSpinner.selectedItem.toString())
                schedule.date = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
                schedule.time = SimpleDateFormat("hh:mm a").format(calendar.time)
                schedule.notes = binding.etAddNotes.text.toString()

                val scheduleAdapter = ScheduleAdapter(this, ScheduleDAOFFirebaseImplementation().getSchedules())
                scheduleAdapter.addSchedule(schedule)

                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}