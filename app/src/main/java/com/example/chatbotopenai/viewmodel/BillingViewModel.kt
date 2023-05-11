package com.app.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.billingclient.api.Purchase
import com.app.model.ChatMessageModel

class BillingViewModel(activity: Activity) : ViewModel() {
    var context = activity
    var purchasedList: MutableLiveData<Purchase>? = MutableLiveData()

}