package com.app.view.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.app.view.listeners.OnListClickListener
import com.app.view.listeners.OnPurchaseListener
import com.talkai.chatgpt.ai.app.R
import makeVisible


abstract class BaseFragment : OnListClickListener, Fragment(), OnPurchaseListener {

    private var tag = this.javaClass.simpleName
    private var lifecycleCheck = "zxczxc"
    private  var mProgressDialog: Dialog?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e(lifecycleCheck, "lifecycle check - $tag onActivityCreated")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(lifecycleCheck, "lifecycle check - $tag onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.e(lifecycleCheck, "lifecycle check - $tag onStart")
    }

    override fun onResume() {
        super.onResume()

        // setFullAppBackGround() //for comman background after dashboard in fragment
        Log.e(lifecycleCheck, "lifecycle check - ${this.tag} onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(lifecycleCheck, "lifecycle check - ${this.tag} onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e(lifecycleCheck, "lifecycle check - ${this.tag} onStop")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(lifecycleCheck, "lifecycle check - ${this.tag} onDetach")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(lifecycleCheck, "lifecycle check - ${this.tag} onDestroyView")
        try {
            //  homeController!!.lastDestroyedFragment = this.javaClass.simpleName
            hideSoftKeyboard()
        } catch (e: Exception) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(lifecycleCheck, "lifecycle check - ${this.tag} onDestroy")

    }

     fun hideSoftKeyboard() {
        if (activity!!.window.decorView.rootView != null) {
            val inputMethodManager =
                activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity!!.window.decorView.rootView.windowToken,
                0
            )
        } else {

        }
    }
fun shoToast(message:String){
    Toast.makeText(activity,message,Toast.LENGTH_SHORT).show()
}
    fun showSoftKeyboard() {
        val inputMethodManager = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager != null) {
            val currentFocusView = activity!!.window.decorView.rootView
            if (currentFocusView != null) {
                val iBinderToken = currentFocusView.windowToken
                if (iBinderToken != null) {
                    inputMethodManager.toggleSoftInputFromWindow(
                        iBinderToken,
                        InputMethodManager.SHOW_FORCED, 0
                    )
                }
            }
        }
    }
    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = Dialog(activity!!)
        }
        try {
            mProgressDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mProgressDialog!!.show()
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.setCanceledOnTouchOutside(false)
            mProgressDialog!!.setContentView(R.layout.dialog_progress)
            mProgressDialog!!.findViewById<ProgressBar>(R.id.progressbar).makeVisible()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun dismissProgressDialog() {
        if (mProgressDialog != null) {
            try {
                mProgressDialog!!.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun String.removeComma(): String {
        return this.replace(",", "")
    }

    /**override methods from implemented interfaces are implemented below*/
    override fun onListClick(position: Int, obj: Any?) {}

    override fun onListClickSimple(position: Int, obj: Any?) {
    }

    override fun onReardedSucessView(obj: Any?) {

    }

    override fun OnProuctPurchaseListener(
        billingResult: BillingResult?,
        purchaselist: List<Purchase>
    ) {
    }

    override fun OnProductDetailListener(
        billingResult: BillingResult?,
        productDetailsList: List<ProductDetails>
    ) {

    }

    override fun OnProuctAcknowledgeListener(billingResult: BillingResult?) {

    }
}
