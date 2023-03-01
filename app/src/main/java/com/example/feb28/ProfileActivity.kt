package com.example.feb28

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.feb28.databinding.ActivtyProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private var binding: ActivtyProfileBinding? = null
    private lateinit var auth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivtyProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        /// set data - ui
        setData(account)

        binding?.textViewLogout?.setOnClickListener {
            logout()
        }

    }

    private fun setData(account: GoogleSignInAccount?) {
        binding?.apply {
            textViewUserName.text = account?.displayName
            textViewUserEmail.text = account?.email
            imageViewProfile.let {
                Glide.with(this@ProfileActivity)
                    .load(account?.photoUrl)
                    .centerCrop()
                    .fitCenter()
                    .into(it)
            }
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        mGoogleSignInClient.signOut()
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }
}