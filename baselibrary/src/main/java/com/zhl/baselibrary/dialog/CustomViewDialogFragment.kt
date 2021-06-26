package com.zhl.baselibrary.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.zhl.baselibrary.R
import com.zhl.baselibrary.utils.AppManager


/**
 *    author : zhuhl
 *    date   : 2021/6/25
 *    desc   : 系统默认样式自定义View的AlertDialog，目前存在的问题是宽度无法定义成wrap_content
 *    refer  :
 */
class CustomViewDialogFragment(
    @LayoutRes private val layoutRes: Int,
    private var confirmBtnText: String?,
    private var cancelBtnText: String?
) : DialogFragment() {

    init {
        //初始化默认的字符串
        if (TextUtils.isEmpty(confirmBtnText)) {
            confirmBtnText = AppManager.getActivity()?.let {
                it.resources.getString(R.string.dialog_btn_confirm)
            } ?: "confirm"
        }
        if (TextUtils.isEmpty(cancelBtnText)) {
            cancelBtnText = AppManager.getActivity()?.let {
                it.resources.getString(R.string.dialog_btn_cancel)
            } ?: "cancel"
        }
    }

    constructor(
        @LayoutRes layoutRes: Int,
    ) : this(
        layoutRes,
        null,
        null
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(layoutRes, null))
            // Add action buttons
            /*
        .setPositiveButton(confirmBtnText,
            DialogInterface.OnClickListener { dialog, id ->
                // sign in the user ...
            })
        .setNegativeButton(cancelBtnText,
            DialogInterface.OnClickListener { dialog, id ->
                getDialog()?.cancel()
            })

             */
            val alertDialog = builder.create()
//            alertDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
//            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            alertDialog.window?.setBackgroundDrawableResource(R.drawable.common_dialog_bg)
//            alertDialog.window?.setLayout(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.MATCH_PARENT
//            )
//            alertDialog.window?.setGravity(Gravity.CENTER)
            return@let alertDialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}