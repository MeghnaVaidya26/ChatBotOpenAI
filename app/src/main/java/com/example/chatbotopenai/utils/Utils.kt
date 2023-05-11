package com.app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.WindowCompat
import com.app.view.*
import com.app.view.listeners.OnListClickListener
import com.app.view.listeners.SubscriptionListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.talkai.chatgpt.ai.app.R
import makeGone

import setOnMyClickListener


class Utils {
    companion object {
        @SuppressLint("RestrictedApi")
        fun createFullScreen(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                activity.window.attributes.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowCompat.setDecorFitsSystemWindows(activity.window, false)
            } else {
                @Suppress("DEPRECATION") activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }

        fun pasteTextFromClipboard(context: Context): String {
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            return clipboardManager.primaryClip?.getItemAt(0)?.text.toString()
        }

        fun copyTextToClipboard(text: String, context: Context) {
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", text)
            clipboardManager.setPrimaryClip(clipData)

        }

        private fun getBottomSheetDialogDefaultHeight(context: Context, percetage: Int): Int {
            return getWindowHeight(context) * percetage / 100
        }

        private fun getWindowHeight(context: Context): Int {
            // Calculate window height for fullscreen use
            val displayMetrics = DisplayMetrics()
            (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }

        fun showBottomSheetDialog(activity: Activity) {
            val bottomSheetDialog =
                BottomSheetDialog(activity, R.style.ThemeOverlay_Demo_BottomSheetDialog)
            bottomSheetDialog.setContentView(R.layout.fragment_rate_us)
            val bottomSheetBehavior = bottomSheetDialog.behavior
            bottomSheetBehavior.isDraggable = true
            val bottomSheet = bottomSheetDialog.findViewById<View>(R.id.llView) as ConstraintLayout
            val layoutParams = bottomSheet.layoutParams
            layoutParams.height = getBottomSheetDialogDefaultHeight(activity, 98)
            bottomSheetBehavior.maxWidth = ViewGroup.LayoutParams.MATCH_PARENT

            bottomSheet.layoutParams = layoutParams
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetDialog.findViewById<TextView>(R.id.tv_maybe_later)!!.setOnMyClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.findViewById<TextView>(R.id.bt_write_a_revie)!!.setOnMyClickListener {
                try {
                    val viewIntent = Intent(
                        "android.intent.action.VIEW",
                        Uri.parse(Constant.reviewLink)
                    )
                    startActivity(activity, viewIntent, null)
                } catch (e: Exception) {
                    Toast.makeText(
                        activity, "Unable to Connect Try Again...",
                        Toast.LENGTH_LONG
                    ).show()


                    e.printStackTrace()
                }

            }

            bottomSheetDialog.findViewById<ImageView>(R.id.iv_close_dialog)!!.setOnMyClickListener {
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.show()
        }

        fun commonDialog(
            activity: Activity, onlistviewclicklistener: OnListClickListener, msg: String
        ) {
            AlertDialog.Builder(activity, R.style.MyDialogTheme).apply {
                this.setMessage(msg)
                this.setPositiveButton(
                    activity.getString(R.string.yes)

                ) { dialog, _ ->
                    dialog.dismiss()
                    onlistviewclicklistener.onListClickView(0, "")
                }
                this.setNegativeButton(activity.getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }

            }.create().apply {
                this.setOnShowListener {
                    this.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(ContextCompat.getColor(activity, R.color.black))
                    this.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(ContextCompat.getColor(activity, R.color.black))
                }
            }.show()

        }


        fun getMessagedialog(context: Activity, subscriptionListener: SubscriptionListener) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_ask_subscription)
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            dialog.setCancelable(false)
            //   dialog.window!!.attributes.windowAnimations = R.style.animation

            var getPremium = dialog.findViewById<View>(R.id.tv_get_premium)
            var watchAds = dialog.findViewById<View>(R.id.tv_watch_ad)
            var ivClose = dialog.findViewById<View>(R.id.iv_close_dialog_sub)
            if (Pref.getMessageCount(
                    context
                ) > Constant.maxMessageCount
            ) {
                watchAds.makeGone()
            }
            ivClose.setOnMyClickListener {
                dialog.dismiss()
            }
            getPremium.setOnMyClickListener {

                subscriptionListener.OnSubOrAdGetClicked(1)
                dialog.dismiss()

            }

            watchAds.setOnMyClickListener {

                subscriptionListener.OnSubOrAdGetClicked(2)
                dialog.dismiss()

            }

            dialog.show()


        }

        enum class Device {
            DEVICE_TYPE
        }

        fun getDeviceInfo(context: Context, device: Device): String? {
            try {
                if (device == Device.DEVICE_TYPE) {
                    return if (isTablet(context)) {
                        if (getDevice5Inch(context)) {
                            "This is Tablet"
                        } else {
                            "This is Mobile"
                        }
                    } else {
                        "This is Mobile"
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return ""
        }

        private fun getDevice5Inch(context: Context): Boolean {
            return try {
                val displayMetrics = context.resources.displayMetrics
                val yinch = displayMetrics.heightPixels / displayMetrics.ydpi
                val xinch = displayMetrics.widthPixels / displayMetrics.xdpi
                val diagonalinch = Math.sqrt((xinch * xinch + yinch * yinch).toDouble())
                diagonalinch >= 7
            } catch (e: java.lang.Exception) {
                false
            }
        }

        private fun isTablet(context: Context): Boolean {
            return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
        }

    }


}
