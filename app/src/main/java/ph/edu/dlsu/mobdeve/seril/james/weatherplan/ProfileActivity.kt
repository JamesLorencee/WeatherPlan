package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityAddScheduleBinding
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DISPLAYING USER INFO through Google Sign in
        val email = intent.getStringExtra("email").toString()
        val displayname = intent.getStringExtra("name").toString()

        binding.userEmailTv.text = email
        binding.usernameTv.text = displayname

        // LOGOUT FUNCTION here (need to terminate user data. No Proper function yet
        auth = FirebaseAuth.getInstance()
        binding.logoutProfileBtn.setOnClickListener{
            auth.signOut()
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