package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

}