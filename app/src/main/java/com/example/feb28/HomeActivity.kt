package com.example.feb28

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.feb28.adapter.GalleryAdapter
import com.example.feb28.dataClass.Data
import com.example.feb28.dataClass.ResponseBody
import com.example.feb28.databinding.ActivityHomeBinding
import com.example.feb28.roomDB.GalleryDatabase
import com.example.feb28.roomDB.GalleryDetail
import com.example.feb28.viewModel.ApiViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private var binding: ActivityHomeBinding? = null

    private var list = ArrayList<Data>()
    private var galleryAdapter: GalleryAdapter? = null

    var viewModel = ApiViewModel()

    private lateinit var galleryDatabase: GalleryDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.textViewProfile?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        /// handle response
        handleResponse()

        galleryDatabase =
            Room.databaseBuilder(this, GalleryDatabase::class.java, "Gallery_DB").build()

        /// condition
        if (checkForInternet(this)) {
            viewModel.callPostApi()
        } else {
            binding?.loader?.visibility = View.GONE
            galleryDatabase.galleryDao().getAllRecord().observe(this) {
                if (it != null) {
                    list.clear()
                    it.forEach {
                        list.add(Data(avatar = it.nameUrl))
                        galleryAdapter?.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this, "Please Internet ON once", Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding?.loader?.visibility = View.VISIBLE
        binding?.recyclerViewPostOffice?.apply {
            layoutManager = GridLayoutManager(context, 2)
            galleryAdapter = GalleryAdapter(list)
            adapter = galleryAdapter
        }
    }

    private fun handleResponse() {
        viewModel.listLiveData.observe(this) { responseBody ->
            when (responseBody) {
                is ResponseBody.Error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
                is ResponseBody.Loading -> {
                    binding?.loader?.visibility = View.VISIBLE
                }
                is ResponseBody.Success -> {
                    binding?.loader?.visibility = View.GONE
                    responseBody.data?.dataList?.let { list.addAll(it) }
                    galleryAdapter?.notifyDataSetChanged()

                    responseBody.data?.dataList?.forEach {
                        GlobalScope.launch {
                            galleryDatabase.galleryDao().insertImage(
                                GalleryDetail(
                                    0,
                                    it.avatar.toString()
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}

fun checkForInternet(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return false

    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}
