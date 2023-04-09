package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleDAOFFirebaseImplementation
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleListener
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.ScheduleAdapter
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityViewScheduleBinding
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.utility.SharedPreferencesUtility
import java.text.SimpleDateFormat

class ViewScheduleActivity : AppCompatActivity(), ScheduleListener {

    private lateinit var binding: ActivityViewScheduleBinding
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var sharedPreferences: SharedPreferencesUtility

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

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scheduleDAO = ScheduleDAOFFirebaseImplementation()
        scheduleDAO.getSchedules(this)

        sharedPreferences = SharedPreferencesUtility(applicationContext)

        val datetime = SimpleDateFormat("yyyy-MM-dd HH:mm").parse("${intent.getStringExtra("date")} ${intent.getStringExtra("time")}")

        binding.viewTitle.text = intent.getStringExtra("title")
        binding.viewEventType.text = intent.getStringExtra("eventtype")
        binding.viewLocation.text = intent.getStringExtra("location")
        binding.viewTime.text = SimpleDateFormat("hh:m a").format(datetime!!)
        binding.viewDate.text = SimpleDateFormat("MMMM dd, yyyy").format(datetime)
        binding.viewNotes.text = "Notes:\n${intent.getStringExtra("notes")}"

        binding.weatherTemperature.text = sharedPreferences.getStringPrefs("weatherTemperature")
        binding.weatherDescription.text = sharedPreferences.getStringPrefs("weatherDescription")

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

        binding.editScheduleBtn.isEnabled = false
    }

    override fun onSchedulesReceived(scheduleList: ArrayList<Schedule>) {
        scheduleAdapter = ScheduleAdapter(this, scheduleList)
        binding.editScheduleBtn.isEnabled = true
    }
}