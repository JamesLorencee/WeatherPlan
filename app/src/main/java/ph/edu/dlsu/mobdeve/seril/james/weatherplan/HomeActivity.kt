package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONObject
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleDAO
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.ScheduleDAOSQLiteImplementation
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.ScheduleAdapter
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityHomeBinding
import java.net.URL

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var scheduleDAO: ScheduleDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scheduleDAO = ScheduleDAOSQLiteImplementation(applicationContext)

        // To Run the weather API
        WeatherTask().execute()

        scheduleAdapter = ScheduleAdapter(this, scheduleDAO.getSchedules())

        binding.scheduleList.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL,
            false)
        binding.scheduleList.adapter = scheduleAdapter

        // When clicked, will move to LIST ACTIVITY page //
        binding.optionsMenu.listTv.setOnClickListener{
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        // When clicked, will move to ADD ACTIVITY page //
        binding.optionsMenu.listAddTv.setOnClickListener{
            val intent = Intent(this, AddScheduleActivity::class.java)
            startActivity(intent)
        }

        // When clicked, will move to ACCOUNT DETAILS page //
        binding.optionsMenu.listAccountTv.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }

// CLASS TO CALL THE API VARIABLES
    @SuppressLint("StaticFieldLeak")
    inner class WeatherTask() : AsyncTask<String, Void, String>(){

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            Log.d ("Weather", "OnPreExecute")
        }
        @Deprecated("Deprecated in Java")
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

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            try{
                val jsonObj = JSONObject(result).getJSONArray("list").getJSONObject(1)
                val main = jsonObj.getJSONObject("main")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val temp = main.getInt("temp").toString() + "°C"
                val weatherDescription = weather.getString("description")

                binding.weatherTemperature.text = temp
                binding.weatherDescription.text = weatherDescription

                Log.d ("Weather", "OnPostExecute")
            }
            catch(e : Exception){
                println(JSONObject(result).getJSONArray("list"))
                println(JSONObject(result).getJSONArray("list").getJSONObject(1).getJSONObject("main"))
            }
        }

    }
    // CLASS TO CALL THE API VARIABLES

}