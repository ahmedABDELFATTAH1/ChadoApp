package com.abdelfattah.cmpbeta

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
class LoginActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var mGoogleSignInClient: GoogleSignInClient?=null
    var  RC_SIGN_IN:Int?=3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        loadmain()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient=GoogleSignIn.getClient(this,gso)

        signingoogle.setOnClickListener {
            signIn()

        }
    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN!!)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

         // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,"Login faild",Toast.LENGTH_SHORT).show()


            }

        }
    }

    override fun onStart() {
        super.onStart()
        loadmain()
    }
    private fun loadmain() {
        var currentuser=mAuth!!.currentUser
        if(currentuser != null) {
            var intent = Intent(this, Main2Activity::class.java)
            intent.putExtra("email",currentuser.email)
            intent.putExtra("id",currentuser.uid)
            intent.putExtra("name",currentuser.displayName)
            intent.putExtra("image",currentuser.photoUrl)
            startActivity(intent)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {


        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
          mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,mAuth!!.currentUser!!.email,Toast.LENGTH_SHORT).show()
                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("users").child(mAuth!!.currentUser!!.uid)
                        val inf=HashMap<String,String>()
                        inf.put("email",mAuth!!.currentUser!!.email.toString())
                        inf.put("photourl",mAuth!!.currentUser!!.photoUrl.toString())
                        inf.put("name",mAuth!!.currentUser!!.displayName.toString())
                        inf.put("uid",mAuth!!.currentUser!!.uid.toString())
                        myRef.setValue(inf)
                        loadmain()
                        }
                    else
                    {
                        Toast.makeText(this,"Login faild",Toast.LENGTH_SHORT).show()
                    }


                }
    }

}

