package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.ScheduleAdapter
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var scheduleAdapter: ScheduleAdapter
    private var scheduleList = getSchedule()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scheduleAdapter = ScheduleAdapter(this, scheduleList)

        binding.scheduleList.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL,
            false)
        binding.scheduleList.adapter = scheduleAdapter
    }

    private fun getSchedule() = ArrayList<Schedule>().apply {
        add(Schedule(
            "MOBDEVE",
            "GK304B",
            "Mobile Development with Sir Marco",
            "2023-03-31",
            "16:15"
        ))
        add(Schedule(
            "Title",
            "Location",
            "Notes",
            "2023-03-31",
            "10:00"
        ))
    }
}