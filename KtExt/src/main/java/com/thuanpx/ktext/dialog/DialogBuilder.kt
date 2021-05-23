package com.thuanpx.ktext.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.thuanpx.ktext.R
import com.thuanpx.ktext.view.clicks

/**
 * Created by ThuanPx on 22/05/2021.
 */
@DslMarker
annotation class DslDialog

@DslDialog
class DialogBuilder(
    val context: Context,
    val themeColor: DialogThemeColor,
    val setup: DialogBuilder.() -> Unit = {}
) {

    var title: String = ""
    var messageText: String = ""
    var positiveText: String = "OK"
    var negativeText: String = "No"
    var positiveAction: (() -> Unit)? = null
    var negativeAction: (() -> Unit)? = null
    var cancelable: Boolean = false
    var isShowNegativeButton = false
    private lateinit var dialog: AlertDialog


    fun build(): AlertDialog {
        setup()
        if (messageText.isEmpty()) {
            throw IllegalArgumentException("You should fill all mandatory fields in the options")
        }
        val options = DialogOptions(
            title = title,
            messageText = messageText,
            backgroundThemeColor = themeColor,
            positiveText = positiveText,
            negativeText = negativeText,
            positiveAction = positiveAction,
            negativeAction = negativeAction,
            cancelable = cancelable,
            isShowNegativeButton = isShowNegativeButton
        )

        dialog = setupCustomAlertDialog(options)

        return dialog
    }


    @SuppressLint("InflateParams")
    private fun setupCustomAlertDialog(options: DialogOptions): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null)

        val alertDialog =
            MaterialAlertDialogBuilder(context, R.style.DialogCustomTheme)
                .setView(view)
                .setCancelable(false)
                .create()

        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = options.title
        tvTitle.setTextColor(ContextCompat.getColor(context, options.backgroundThemeColor.titleTextColor))

        val tvMessage = view.findViewById<TextView>(R.id.tvMessage)
        tvMessage.text = options.messageText
        tvMessage.setTextColor(ContextCompat.getColor(context, options.backgroundThemeColor.messageTextColor))


        val buttonNegative = view.findViewById<TextView>(R.id.btNegative)
        buttonNegative.setTextColor(ContextCompat.getColor(context, options.backgroundThemeColor.negativeTextColor))
        buttonNegative.visibility = if (isShowNegativeButton) View.VISIBLE else View.GONE
        buttonNegative.text = options.negativeText
        buttonNegative.clicks {
            options.negativeAction?.invoke()
            alertDialog.dismiss()
        }

        val buttonPositive = view.findViewById<TextView>(R.id.btPositive)
        buttonPositive.setTextColor(ContextCompat.getColor(context, options.backgroundThemeColor.positiveTextColor))
        buttonPositive.text = options.positiveText
        buttonPositive.clicks {
            options.positiveAction?.invoke()
            alertDialog.dismiss()
        }


        return alertDialog
    }
}

fun Fragment.dialog(setup: DialogBuilder.() -> Unit) {
    val builder = DialogBuilder(requireContext(), themeColor = DialogThemeColor.Blue, setup = setup)
    builder.build().show()
}

fun Activity.dialog(setup: DialogBuilder.() -> Unit) {
    val builder = DialogBuilder(this, themeColor = DialogThemeColor.Blue, setup = setup)
    builder.build().show()
}

fun AlertDialog.dismiss() {
    if (isShowing) {
        dismiss()
    }
}


data class DialogOptions(
    val title: String,
    val messageText: String,
    val backgroundThemeColor: DialogThemeColor,
    val positiveText: String,
    val negativeText: String,
    val positiveAction: (() -> Unit)? = null,
    val negativeAction: (() -> Unit)? = null,
    val cancelable: Boolean,
    val isShowNegativeButton: Boolean
)

sealed class DialogThemeColor {
    val backgroundColor: Int
        get() = when (this) {
            is Blue -> android.R.color.white
        }

    val messageTextColor: Int
        get() = when (this) {
            is Blue -> android.R.color.black
        }

    val titleTextColor: Int
        get() = when (this) {
            is Blue -> android.R.color.black
        }

    val positiveTextColor: Int
        get() = when (this) {
            is Blue -> R.color.blue_700
        }

    val negativeTextColor: Int
        get() = when (this) {
            is Blue -> R.color.blue_700
        }

    object Blue : DialogThemeColor()
}