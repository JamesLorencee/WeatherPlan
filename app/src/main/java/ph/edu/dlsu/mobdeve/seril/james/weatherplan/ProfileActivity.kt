package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityAddScheduleBinding
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // LOGOUT FUNCTION here (need to terminate user data. No Proper function yet
        binding.logoutProfileBtn.setOnClickListener{
            val logout = Intent(this, MainActivity::class.java)
            startActivity(logout)

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