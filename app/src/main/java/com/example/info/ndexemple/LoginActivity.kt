package com.example.info.ndexemple

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.support.v4.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthCredential
import com.google.android.gms.auth.api.signin.GoogleSignInAccount






class LoginActivity : AppCompatActivity(),GoogleApiClient.OnConnectionFailedListener {


    override fun onConnectionFailed(connectinoresult: ConnectionResult) {
         Log.d("Erro","Erro ao Tentar Logar com "+connectinoresult);
            }

    private val RC_SING_IN = 9001;
    private var mGoolgeApiClient: GoogleApiClient? = null;
    private var btnLogin: Button? = null;




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)





        btnLogin = findViewById(R.id.btnLogin) as Button;
        
        updateUI(false)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()


        mGoolgeApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build()

        btnLogin?.setOnClickListener(View.OnClickListener {
            var signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoolgeApiClient)
            startActivityForResult(signInIntent, RC_SING_IN)
        })






    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SING_IN){
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            updateUI(result.isSuccess);

        }

    }





    private fun updateUI(isLogin : Boolean) {
        if (isLogin){
            startActivity(Intent(this@LoginActivity, AddActivity::class.java))
            btnLogin?.visibility = View.GONE;
        }else{
            btnLogin?.visibility = View.VISIBLE;}


    }





    }

