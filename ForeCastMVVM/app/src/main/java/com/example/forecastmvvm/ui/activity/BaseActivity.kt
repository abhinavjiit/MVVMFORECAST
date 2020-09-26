package com.example.forecastmvvm.ui.activity

import android.app.ProgressDialog
import android.content.DialogInterface
import android.view.KeyEvent
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.forecastmvvm.R

open class BaseActivity : AppCompatActivity() {
    private var mProgressDialog: ProgressDialog? = null
    fun showProgressDialog(bodyText: String?) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this, R.style.MyAlertDialogStyle)
            mProgressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mProgressDialog?.setCancelable(false)
            mProgressDialog?.setCanceledOnTouchOutside(false)
            mProgressDialog?.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event -> keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_SEARCH })
        }
        mProgressDialog?.setMessage(bodyText)
        if (!mProgressDialog?.isShowing!!) {
            mProgressDialog?.show()
        }
    }

    fun removeProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }
}