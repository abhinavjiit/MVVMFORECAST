package com.example.forecastmvvm.ui.fragment

import android.app.ProgressDialog
import android.content.DialogInterface
import android.view.KeyEvent
import android.view.Window
import androidx.fragment.app.Fragment
import com.example.forecastmvvm.R


open class BaseFragment : Fragment() {
    private var mProgressDialog: ProgressDialog? = null
    fun showProgressDialog(bodyText: String?) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(activity, R.style.MyAlertDialogStyle)
            mProgressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mProgressDialog?.setCancelable(false)
            mProgressDialog?.setCanceledOnTouchOutside(false)
            mProgressDialog?.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event -> keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_SEARCH })
        }
        mProgressDialog?.setMessage(bodyText)
        if (!mProgressDialog?.isShowing!! && isAdded) {
            mProgressDialog?.show()
        }
    }

    fun removeProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing && isAdded) {
            mProgressDialog!!.dismiss()
        }
    }


}
