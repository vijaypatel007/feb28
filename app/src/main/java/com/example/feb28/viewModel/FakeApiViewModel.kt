package com.example.feb28.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feb28.dataClass.ApiDemo
import com.example.feb28.dataClass.ResponseBody
import com.example.feb28.provider.RetrofitProvider
import kotlinx.coroutines.launch

class ApiViewModel : ViewModel() {
    private val _listLiveData = MutableLiveData<ResponseBody<ApiDemo>>()

    val listLiveData: LiveData<ResponseBody<ApiDemo>> = _listLiveData

    fun callPostApi() {

        viewModelScope.launch {
            val result = RetrofitProvider.getIns().apiService.getGalleryData()

            if (result.isSuccessful) {
                if (result.body()?.dataList?.isNotEmpty() == true) {
                    val data = result.body()
                    _listLiveData.postValue(ResponseBody.Success(data!!))

                } else {
                    _listLiveData.postValue(ResponseBody.Error("No List Found"))
                }
            } else {
                _listLiveData.postValue(ResponseBody.Error("${result.errorBody()}"))
            }
        }
    }
}