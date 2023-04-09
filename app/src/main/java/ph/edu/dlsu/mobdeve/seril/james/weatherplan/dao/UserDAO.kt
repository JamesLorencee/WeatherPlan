package ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.User

interface UserDAO {
    fun addUser(user: User)
    fun getUsers(): ArrayList<User>
    fun updateUser(user: User)
}

class UserDAOFirebaseImplementation : UserDAO {
    private lateinit var firebase: FirebaseDatabase

    override fun addUser(user: User) {
        firebase = Firebase.database
        val userRoot = firebase.reference.child("root").child("users")
        println(user.username)

        userRoot.push().setValue(user)
    }

    override fun getUsers(): ArrayList<User> {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: User) {
        TODO("Not yet implemented")
    }
}
