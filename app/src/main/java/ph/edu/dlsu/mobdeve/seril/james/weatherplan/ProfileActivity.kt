package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityProfileBinding
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.utility.SharedPreferencesUtility

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private lateinit var sharedPreferences: SharedPreferencesUtility

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = SharedPreferencesUtility(applicationContext)

        binding.userEmailTv.text = sharedPreferences.getStringPrefs("account_email")
        // binding.usernameTv.text = intent.getStringExtra("name").toString()

        // LOGOUT FUNCTION here (need to terminate user data. No Proper function yet
        auth = FirebaseAuth.getInstance()
        binding.logoutProfileBtn.setOnClickListener{
            auth.signOut()
            sharedPreferences.setStringPrefs("account_email", "")
            sharedPreferences.setStringPrefs("account_password", "")
            startActivity(Intent(this,MainActivity::class.java))

        }

        // EDIT PROFILE FUNCTION
        binding.editProfileBtn.setOnClickListener{
            val goToEditProfile = Intent(this, EditProfileActivity::class.java)
            startActivity(goToEditProfile)

        }

        // HOMEPAGE ACTIVITY //
        binding.optionsMenu.listTodayTv.setOnClickListener{
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

        // LISTVIEW PAGE //
        binding.optionsMenu.listTv.setOnClickListener{
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        // When clicked, will move to add activity page //
        binding.optionsMenu.listAddTv.setOnClickListener{
            val intent = Intent(this, AddScheduleActivity::class.java)
            startActivity(intent)
        }

    }
}