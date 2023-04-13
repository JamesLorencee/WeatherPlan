package ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.User

interface UserDAO {
    fun addUser(user: User)
    fun updateUser(user: User)
}

class UserDAOFirebaseImplementation : UserDAO {
    private lateinit var firebase: FirebaseDatabase
    private var auth = FirebaseAuth.getInstance()

    override fun addUser(user: User) {
        firebase = Firebase.database
        //userRoot di pa alam ung path
        val userRoot = firebase.reference.child("root").child("users")
        println(user.username)

        userRoot.child(user.id!!).setValue(user)
    }

    override fun updateUser(user: User) {
        firebase = Firebase.database
        val userRoot = firebase.reference.child("root").child("users").child(auth.currentUser!!.uid)

        userRoot.child("username").setValue(user.username)
        userRoot.child("email").setValue(user.email)
    }
}
