package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleDAOFFirebaseImplementation
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.ScheduleAdapter
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityViewScheduleBinding

class ViewScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewScheduleBinding

    @SuppressLint("SetTextI18n")
    private val launchEdit = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->
        val data = result.data

        binding.viewTitle.text = data!!.getStringExtra("title")
        binding.viewEventType.text = data.getStringExtra("eventtype")
        binding.viewLocation.text = data.getStringExtra("location")
        binding.viewTime.text = data.getStringExtra("time")
        binding.viewDate.text = data.getStringExtra("date")
        binding.viewNotes.text = "Notes:\n${intent.getStringExtra("notes")}"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewTitle.text = intent.getStringExtra("title")
        binding.viewEventType.text = intent.getStringExtra("eventtype")
        binding.viewLocation.text = intent.getStringExtra("location")
        binding.viewTime.text = intent.getStringExtra("time")
        binding.viewDate.text = intent.getStringExtra("date")
        binding.viewNotes.text = "Notes:\n${intent.getStringExtra("notes")}"

        binding.optionsMenu.listTodayTv.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.optionsMenu.listTv.setOnClickListener{
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        binding.optionsMenu.listAddTv.setOnClickListener{
            val intent = Intent(this, AddScheduleActivity::class.java)
            startActivity(intent)
        }

        binding.optionsMenu.listAccountTv.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.cancelScheduleBtn.setOnClickListener{
            val scheduleAdapter = ScheduleAdapter(this, ScheduleDAOFFirebaseImplementation().getSchedules())
            scheduleAdapter.deleteSchedule(intent.getIntExtra("position", -99))

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.editScheduleBtn.setOnClickListener{
            val goToEditScheduleActivity = Intent(this, EditScheduleActivity::class.java)

            goToEditScheduleActivity.putExtra("id", this.intent.getIntExtra("id", -999))
            goToEditScheduleActivity.putExtra("title", this.intent.getStringExtra("title"))
            goToEditScheduleActivity.putExtra("eventtype", this.intent.getStringExtra("eventtype"))
            goToEditScheduleActivity.putExtra("location", this.intent.getStringExtra("location"))
            goToEditScheduleActivity.putExtra("time", this.intent.getStringExtra("time"))
            goToEditScheduleActivity.putExtra("date", this.intent.getStringExtra("date"))
            goToEditScheduleActivity.putExtra("notes", this.intent.getStringExtra("notes"))
            goToEditScheduleActivity.putExtra("position", this.intent.getIntExtra("position", -999))

            launchEdit.launch(goToEditScheduleActivity)
        }
    }
}