package ph.edu.dlsu.mobdeve.seril.james.weatherplan

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.etEditUsername.setText(firebaseAuth.currentUser!!.displayName)
        binding.etEditEmail.setText(firebaseAuth.currentUser!!.email)

        val currentUser = FirebaseAuth.getInstance().currentUser

        binding.editProfileSaveBtn.setOnClickListener {
            val username = binding.etEditUsername.text.toString()
            val email = binding.etEditEmail.text.toString()
            val currentPassword = binding.etEditPassword.text.toString()
            val newPassword = binding.etEditConfirmPassword.text.toString()

            currentUser?.let {

                if (email.isNotEmpty()) {
                    currentUser.updateEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User email address updated.")
                                Toast.makeText(this, "User Email Updated.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }

                // Check if password has been changed
                if (newPassword.isNotEmpty() && currentPassword.isNotEmpty() && newPassword == binding.etEditConfirmPassword.text.toString()) {
                    currentUser.updatePassword(newPassword).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User password updated.")
                            Toast.makeText(this, "User Password Updated.", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Log.d(TAG, "Failed to update password.")
                            Toast.makeText(this, "Failed to update password.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                if (username.isNotEmpty()) {
                    val profileChangeReq = userProfileChangeRequest {
                        displayName = username
                    }
                    currentUser.updateProfile(profileChangeReq)
                }
            }
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }



        binding.editProfileCancelBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

    }
}