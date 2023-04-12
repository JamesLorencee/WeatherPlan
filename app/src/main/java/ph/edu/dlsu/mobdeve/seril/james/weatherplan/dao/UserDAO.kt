package ph.edu.dlsu.mobdeve.seril.james.weatherplan.dao

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.Schedule
import ph.edu.dlsu.mobdeve.seril.james.weatherplan.data.model.User

interface UserListener {
    fun onUsersReceived(userList: ArrayList<User>)
}

interface UserDAO {
    fun addUser(user: User)
    fun getUser(listener: UserListener)
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

    override fun getUser(listener: UserListener) {
        firebase = Firebase.database

        readData(object : UserDAOFirebaseImplementation.UserCallback {
            override fun onCallback(userList: ArrayList<User>) {
                listener.onUsersReceived(userList)
            }})


    }

    override fun updateUser(user: User) {
        firebase = Firebase.database
        val userRoot = firebase.reference.child("root").child("users").child(auth.currentUser!!.uid)

        userRoot.child("username").setValue(user.username)
        userRoot.child("email").setValue(user.email)
    }

    private fun readData (userCallback: UserDAOFirebaseImplementation.UserCallback) {
        val userList = ArrayList<User>()
        val userRoot = Firebase.database.reference.child("root").child("users")

        userRoot.get().addOnSuccessListener {
            val users = it.children

            for (user in users) {
                val data = user.getValue(User::class.java)!!
                val newUser = User()
                newUser.id = data.id
                newUser.username = data.username
                newUser.email = data.email
                userList.add(newUser)
            }
            userCallback.onCallback(userList)
        }.addOnFailureListener {
            Log.e("ERROR", "UNABLE TO RETRIEVE DATA")
        }
    }


    private interface UserCallback {
        fun onCallback (userList: ArrayList<User>)
    }
}
