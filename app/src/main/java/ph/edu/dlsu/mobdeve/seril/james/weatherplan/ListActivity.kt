package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleDAOFirebaseImplementation
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleListener
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.ScheduleAdapter
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityListBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListActivity : AppCompatActivity(), ScheduleListener {
    private lateinit var binding: ActivityListBinding
    private lateinit var scheduleAdapter: ScheduleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scheduleDAO = ScheduleDAOFirebaseImplementation()
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

    @SuppressLint("SimpleDateFormat")
    override fun onSchedulesReceived(scheduleList: ArrayList<Schedule>) {
        val nowString = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
        val list: ArrayList<Schedule> = ArrayList(scheduleList
            .filter { it.date!! >= nowString }
            .sortedWith(compareBy<Schedule> {it.date}
                .thenBy { it.time }))

        scheduleAdapter = ScheduleAdapter(this, list)
        binding.scheduleList.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL,
            false)
        binding.scheduleList.adapter = scheduleAdapter
    }
}