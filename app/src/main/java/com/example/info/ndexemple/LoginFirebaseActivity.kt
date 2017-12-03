package com.example.info.ndexemple

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.google.android.gms.common.SignInButton
import kotlinx.android.synthetic.main.activity_login_firebase.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.Auth
import android.content.Intent
import android.os.Handler
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import android.widget.Toast
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.support.v7.app.AlertDialog
import android.text.method.HideReturnsTransformationMethod
import android.widget.TextView
import com.example.info.ndexemple.Util.SessionController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.content_login_firebase.*
import java.util.*
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.nav_header_nddraw.view.*


class LoginFirebaseActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {


    override fun onConnectionFailed(connectinoresult: ConnectionResult) {
        Log.d("Erro", "Erro ao Tentar Logar com " + connectinoresult);
    }

    private var mGoogleBtn: SignInButton? = null
    private var RC_SIGN_IN = 9001;
    private var mGoogleApiClient: GoogleApiClient? = null;
    private var mAuth: FirebaseAuth? = null;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_firebase)
        setSupportActionBar(toolbar)

        mAuth = FirebaseAuth.getInstance();

        updateUI(false)

        mGoogleBtn = findViewById(R.id.googlebtn) as SignInButton;


        mAuth = FirebaseAuth.getInstance();




        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        mGoogleBtn?.setOnClickListener(View.OnClickListener {
            var signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN);

        }
        )


    }


    private fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {                // Google Sign In was successful, authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account)
                updateUI(result.isSuccess)
            } else {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,"Falha ao tentar logar",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.getIdToken(), null)
        mAuth?.signInWithCredential(credential)?.addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information

                Log.d("Sucesso", "signInWithCredential:success")
                val user = mAuth?.getCurrentUser()

            } else {
                // If sign in fails, display a message to the user.
                Log.w("Erro", "signInWithCredential:failure", task.exception)
                Toast.makeText(this@LoginFirebaseActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

            }

            // ...
        })
    }


    public override fun onStart() {
        super.onStart()

        val currentUser = mAuth?.currentUser
        if (currentUser != null) {
            updateUI(true)
        }
    }


    private fun updateUI(result: Boolean) {
        var i = Intent(this@LoginFirebaseActivity, NddrawActivity::class.java)


        if (result) {
            startActivity(Intent(i))

        }


    }
}



