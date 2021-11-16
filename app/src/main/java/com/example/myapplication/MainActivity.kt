package com.example.myapplication

import android.graphics.Insets.add
import android.os.Bundle
import android.text.InputType
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginLeft
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.AuthViewmodel
import com.example.myapplication.models.Messages
import com.example.myapplication.models.MessagesViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val messagesViewModel : MessagesViewModel by viewModels()
    val authViewmodel : AuthViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            if (authViewmodel.userLiveData.value?.email.toString() === "null"){
            Snackbar.make(view, "You need to be logged in", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()}
            else {
                showDialog()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.logout -> {
                if(Firebase.auth.currentUser != null){
                    Firebase.auth.signOut()
                    val navController = findNavController(R.id.nav_host_fragment_content_main)
                    navController.popBackStack(R.id.FirstFragment, false)
                }else{

                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Add Message")

        val layout = LinearLayout(this@MainActivity)
        layout.orientation = LinearLayout.VERTICAL

        val messageInputField = EditText(this)
        messageInputField.hint = "Message"
        messageInputField.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(messageInputField)


        builder.setView(layout)

        builder.setPositiveButton("Send Besked") { dialog, which ->
            val contentPost = messageInputField.text.toString().trim()
            when {
                contentPost.isEmpty() ->
                    //inputField.error = "No words"
                    Snackbar.make(binding.root, "No Message", Snackbar.LENGTH_LONG).show()
                contentPost.isEmpty() -> Snackbar.make(binding.root, "No Message", Snackbar.LENGTH_LONG)
                    .show()
                else -> {
                    val addmessage = Messages(contentPost, authViewmodel.userLiveData.value?.email.toString() ,
                    totalComments = 0)
                    messagesViewModel.add(addmessage)
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }
}