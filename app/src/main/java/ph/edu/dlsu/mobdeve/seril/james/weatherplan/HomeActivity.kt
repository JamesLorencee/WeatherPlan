package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONObject
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.ScheduleAdapter
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityHomeBinding
import java.net.URL

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var scheduleAdapter: ScheduleAdapter
    private var scheduleList = getSchedule()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // To Run the weather API
        weatherTask().execute()

        scheduleAdapter = ScheduleAdapter(this, scheduleList)

        binding.scheduleList.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL,
            false)
        binding.scheduleList.adapter = scheduleAdapter

        // When clicked, will move to list activity page //
        binding.optionList.setOnClickListener{
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
    }

// CLASS TO CALL THE API VARIABLES (NOT WORKING)
    inner class weatherTask() : AsyncTask<String, Void, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d ("Weather", "OnPreExecute")
        }
        override fun doInBackground(vararg p0: String?): String? {
            var response: String?
            try {
                response = URL("https://api.openweathermap.org/data/2.5/forecast?q=manila,ph&units=metric&appid=7836b1b288c389a89ebe52ac3012ff0a")
                    .readText(Charsets.UTF_8)
            }
            catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try{
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val temp = main.getString("temp") + "°C"
                val weatherDescription = weather.getString("description")

                findViewById<TextView>(R.id.weather_temperature).text = temp
                findViewById<TextView>(R.id.weather_description).text = weatherDescription


                binding.weatherDescription.text = temp
                binding.weatherTemperature.text = weatherDescription
                Log.d ("Weather", "OnPostExecute")
            }
            catch(e : Exception){
            }
        }

    }
    // CLASS TO CALL THE API VARIABLES (NOT WORKING)

    // Dummy Data for Schedule //
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
        add(Schedule(
            "Birthday Party",
            "BGC",
            "Notes",
            "2023-03-31",
            "22:00"
        ))
    }


}