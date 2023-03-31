package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityAddScheduleBinding
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityHomeBinding

class AddScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddScheduleBinding

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

        // If User Canceled the add sched, it will return to the home activity
        binding.addSchedCancelBtn.setOnClickListener{
            val goToHome = Intent(this, HomeActivity::class.java)
            startActivity(goToHome)
        }

    }
}