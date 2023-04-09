package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao.UserDAOFirebaseImplementation
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.User
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityRegisterBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // BACK TO MAIN ACTIVITY (Login)
        binding.backLoginBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // SIGN UP BUTTON. Will sign up and store to database
        binding.signupBtn.setOnClickListener{
            val username = binding.etRegisterUsername.text.toString()
            val email = binding.etRegisterEmail.text.toString()
            val password = binding.etRegisterPassword.text.toString()
            val confirmPassword = binding.etRegisterConfirmPassword.text.toString()

            // Creating account, should have matching password and no empty fields
            if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if(password == confirmPassword){

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if(it.isSuccessful){
                            val userDAO = UserDAOFirebaseImplementation()
                            val newUser = User()

                            val scheduleList = ArrayList<Schedule>()
                            val dummySchedule = Schedule()
                            dummySchedule.title = "My First Schedule"
                            dummySchedule.location = "My Android Phone"
                            dummySchedule.event = Schedule().getEnumType("EVENT")
                            dummySchedule.date = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
                            dummySchedule.time = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
                            dummySchedule.notes = "My First Notes"
                            scheduleList.add(dummySchedule)

                            newUser.id = it.result.user!!.uid
                            newUser.username = username
                            newUser.email = it.result.user!!.email
                            newUser.scheduleList = scheduleList
                            userDAO.addUser(newUser)

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    Toast.makeText(this, "Password is not matching.", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "No Empty Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }



}