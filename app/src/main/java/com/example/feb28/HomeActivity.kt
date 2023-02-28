package com.example.feb28

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.feb28.databinding.ActivityHomeBinding
import com.example.feb28.adapter.GalleryAdapter

class HomeActivity : AppCompatActivity() {

    private var binding: ActivityHomeBinding? = null

    private var list = ArrayList<String>()
    private var galleryAdapter: GalleryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.textViewProfile?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        list.add("https://images.pexels.com/photos/1032650/pexels-photo-1032650.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1")
        list.add("https://images.pexels.com/photos/386025/pexels-photo-386025.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1/photos/1032650/pexels-photo-1032650.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1")
        list.add("https://images.pexels.com/photos/1263986/pexels-photo-1263986.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1/photos/386025/pexels-photo-386025.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1/photos/1032650/pexels-photo-1032650.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1")
        list.add("https://images.pexels.com/photos/3358707/pexels-photo-3358707.png?auto=compress&cs=tinysrgb&w=600")
        list.add("https://images.pexels.com/photos/5429056/pexels-photo-5429056.png?auto=compress&cs=tinysrgb&w=600")
        list.add("https://images.pexels.com/photos/5589865/pexels-photo-5589865.jpeg?auto=compress&cs=tinysrgb&w=600")
        list.add("https://images.pexels.com/photos/5536030/pexels-photo-5536030.jpeg?auto=compress&cs=tinysrgb&w=600")
        list.add("https://images.pexels.com/photos/773471/pexels-photo-773471.jpeg?auto=compress&cs=tinysrgb&w=600")
        list.add("https://images.pexels.com/photos/3081750/pexels-photo-3081750.jpeg?auto=compress&cs=tinysrgb&w=600")

        binding?.recyclerViewPostOffice?.apply {
            layoutManager = GridLayoutManager(context, 2)
            galleryAdapter = GalleryAdapter(list)
            adapter = galleryAdapter
        }
    }
}