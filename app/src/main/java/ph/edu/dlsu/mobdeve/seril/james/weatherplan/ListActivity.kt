package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleDAOFFirebaseImplementation
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleListener
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.ScheduleAdapter
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityListBinding

class ListActivity : AppCompatActivity(), ScheduleListener {
    private lateinit var binding: ActivityListBinding
    private lateinit var scheduleAdapter: ScheduleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scheduleDAO = ScheduleDAOFFirebaseImplementation()
        scheduleDAO.getSchedules(this)

        // Directs to HomeActivity
        binding.optionsMenu.listTodayTv.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // Directs to AddScheduleActivity
        binding.optionsMenu.listAddTv.setOnClickListener{
            val intent = Intent(this, AddScheduleActivity::class.java)
            startActivity(intent)
        }

        // Directs to Profile Activity
        binding.optionsMenu.listAccountTv.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onSchedulesReceived(scheduleList: ArrayList<Schedule>) {
        scheduleAdapter = ScheduleAdapter(this, scheduleList)
        binding.scheduleList.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL,
            false)
        binding.scheduleList.adapter = scheduleAdapter
    }
}