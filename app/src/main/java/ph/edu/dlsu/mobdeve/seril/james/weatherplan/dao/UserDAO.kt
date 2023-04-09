package ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.User

interface UserDAO {
    fun addUser(user: User)
    suspend fun getUser(uid: String): User
    fun updateUser(user: User)
}

class UserDAOFirebaseImplementation : UserDAO {
    private lateinit var firebase: FirebaseDatabase
    private var auth = FirebaseAuth.getInstance()

    override fun addUser(user: User) {
        firebase = Firebase.database
        val userRoot = firebase.reference.child("root").child("users")
        println(user.username)

        userRoot.child(user.id!!).setValue(user)
    }

    override suspend fun getUser(uid: String): User {
        firebase = Firebase.database
        val userRoot = firebase.reference.child(uid)
        var result = User()
        userRoot.get().addOnCompleteListener{
            result = it.result.getValue(User::class.java)!!
        }.await()

        println(result.username)
        return result
    }

    override fun updateUser(user: User) {
        firebase = Firebase.database
        val userRoot = firebase.reference.child("root").child("users").child(auth.currentUser!!.uid)

        userRoot.child("username").setValue(user.username)
        userRoot.child("email").setValue(user.email)
    }
}
