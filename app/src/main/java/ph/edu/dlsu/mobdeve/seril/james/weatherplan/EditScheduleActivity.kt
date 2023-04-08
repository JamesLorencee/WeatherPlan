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
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityEditScheduleBinding
import java.text.SimpleDateFormat
import java.util.*

class EditScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditScheduleBinding

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        val datetime = SimpleDateFormat("MMMM dd, yyyy hh:mm a")
            .parse("${intent.getStringExtra("date").toString()} ${intent.getStringExtra("time").toString()}")

        binding.editTimePicker.setIs24HourView(true)
        binding.editTimePicker.hour = SimpleDateFormat("HH").format(datetime!!).toInt()
        binding.editTimePicker.minute = SimpleDateFormat("mm").format(datetime).toInt()

        binding.datePicker.updateDate(
            SimpleDateFormat("yyyy").format(datetime).toInt(),
            SimpleDateFormat("MM").format(datetime).toInt(),
            SimpleDateFormat("dd").format(datetime).toInt()
            )

        binding.etEditTitle.setText(intent.getStringExtra("title"))
        binding.etEditLocation.setText(intent.getStringExtra("location"))
        binding.etEditNotes.setText(intent.getStringExtra("notes"))

        binding.editSchedSaveBtn.setOnClickListener {
            if (binding.etEditTitle.text.isNotEmpty()
                && binding.etEditLocation.text.isNotEmpty()
            ) {
                val intent = Intent()

                val calendar: Calendar = Calendar.getInstance()
                calendar.set(binding.datePicker.year, binding.datePicker.month, binding.datePicker.dayOfMonth, binding.editTimePicker.hour, binding.editTimePicker.minute)

                val schedule = Schedule(
                    this.intent.getIntExtra("id", -999),
                    binding.etEditTitle.text.toString(),
                    binding.etEditLocation.text.toString(),
                    Schedule().getEnumType(binding.editEventTypeSpinner.selectedItem.toString()),
                    SimpleDateFormat("yyyy-MM-dd").format(calendar.time),
                    SimpleDateFormat("HH:mm").format(calendar.time),
                    binding.etEditNotes.text.toString()
                )

                val scheduleAdapter = ScheduleAdapter(this, ScheduleDAOFFirebaseImplementation().getSchedules())
                scheduleAdapter.editSchedule(schedule, this.intent.getIntExtra("position", -999))

                intent.putExtra("id", this.intent.getIntExtra("id", -999))
                intent.putExtra("title", schedule.title)
                intent.putExtra("location", schedule.location)
                intent.putExtra("eventtype", schedule.event.toString())
                intent.putExtra("time", schedule.time)
                intent.putExtra("date", schedule.date)
                intent.putExtra("notes", schedule.notes)
                intent.putExtra("position", this.intent.getIntExtra("position", -999))
                setResult(1, intent)
                finish()
            }
        }

        binding.editSchedCancelBtn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("id", this.intent.getIntExtra("id", -999))
            intent.putExtra("title", this.intent.getStringExtra("title"))
            intent.putExtra("location", this.intent.getStringExtra("location"))
            intent.putExtra("eventtype", this.intent.getStringExtra("eventtype"))
            intent.putExtra("time", this.intent.getStringExtra("time"))
            intent.putExtra("date", this.intent.getStringExtra("date"))
            intent.putExtra("notes", this.intent.getStringExtra("notes"))
            intent.putExtra("position", this.intent.getIntExtra("position", -999))
            setResult(0, intent)
            finish()
        }
    }
}