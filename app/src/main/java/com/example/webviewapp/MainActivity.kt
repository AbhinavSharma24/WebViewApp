package com.example.webviewapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.webkit.WebViewClient
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val MY_REQUEST_CODE = 1234
    lateinit var providers: List<AuthUI.IdpConfig>

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        providers = Arrays.asList(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        showSignInOptions()

        signOutBtn.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    signOutBtn.isEnabled = false
                    showSignInOptions()
                }.addOnFailureListener {
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                }
        }


        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://www.google.com")

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true


        MySite.setOnClickListener {
            startActivity(Intent(this,MySiteActivity::class.java))
        }
    }

    private fun showSignInOptions() {
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.MyTheme)
                .build(),MY_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MY_REQUEST_CODE){
            val idpResponse = IdpResponse.fromResultIntent(data)
            if(resultCode ==  Activity.RESULT_OK){
                val firebaseUser = FirebaseAuth.getInstance().currentUser

                Toast.makeText(this,""+firebaseUser!!.email,Toast.LENGTH_SHORT).show()
                signOutBtn.isEnabled = true
            }
            else{
                Toast.makeText(this,""+idpResponse!!.error!!.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack()
        }
        else{
            super.onBackPressed()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //Inflate the menu //this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        //val navController = findNavController(R.id.nav_host_fragment)
        //return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/

}
