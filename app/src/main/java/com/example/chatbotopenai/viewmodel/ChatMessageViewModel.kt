package com.app.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.model.ChatMessageModel
import com.app.model.ParamModel
import com.app.utils.Constant
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatMessageViewModel(activity: Activity) : ViewModel() {
    var context = activity
    var messageListModel: MutableLiveData<ChatMessageModel>? = MutableLiveData()
    var isLoading: MutableLiveData<Boolean>? = MutableLiveData()
    var responseError: MutableLiveData<ResponseBody>? = MutableLiveData()

    fun getMessage(param: ParamModel) {
        isLoading?.value = true
        ApiClient.getClient(context).loadMessage(param = param, authorization = Constant.apiKey)
            .enqueue(object : Callback<ChatMessageModel> {
                override fun onResponse(
                    call: Call<ChatMessageModel>, response: Response<ChatMessageModel>
                ) {
                    isLoading?.value = false
                    if (response.isSuccessful) {
                        messageListModel!!.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ChatMessageModel>, t: Throwable) {
                    isLoading?.value = false
                }

            })
    }
}