@file:JvmName("KtExtContext")
@file:JvmMultifileClass

package com.thuanpx.ktext.context

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.thuanpx.ktext.AnimationType
import com.thuanpx.ktext.Constant
import com.thuanpx.ktext.SLIDE_TO_LEFT
import kotlin.reflect.KClass

/**
 * Created by ThuanPx on 3/15/20.
 */


fun <T : Activity> FragmentActivity.goTo(
    cls: KClass<T>,
    bundle: Bundle? = null,
    parcel: Parcelable? = null
) {
    intent = Intent(this, cls.java)
    if (bundle != null) intent.putExtra(Constant.KTEXT_EXTRA_ARGS, bundle)
    if (parcel != null) intent.putExtra(Constant.KTEXT_EXTRA_ARGS, parcel)
    startActivity(intent)
}

fun FragmentActivity.rootTo(
    @NonNull clazz: KClass<out Activity>,
    args: Bundle? = null
) {
    val intent = Intent(this, clazz.java)
    args?.let {
        intent.putExtras(it)
    }
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    this.startActivity(intent)
}

fun FragmentActivity.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.simpleName,
    @AnimationType animateType: Int = SLIDE_TO_LEFT
) {
    supportFragmentManager.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        replace(containerId, fragment, tag)
    }, animateType = animateType)
}

fun FragmentActivity.addFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.simpleName,
    @AnimationType animateType: Int = SLIDE_TO_LEFT
) {
    supportFragmentManager.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        add(containerId, fragment, tag)
    }, animateType = animateType)
}

fun FragmentActivity.isVisibleFragment(tag: String): Boolean {
    val fragment = supportFragmentManager.findFragmentByTag(tag)
    return fragment?.isAdded ?: false && fragment?.isVisible ?: false
}

inline fun <reified T : Any> FragmentActivity.getFragment(clazz: KClass<T>): T? {
    val tag = clazz.java.simpleName
    return supportFragmentManager.findFragmentByTag(tag) as? T?
}

/**
 * val test = extra<String>("test")
 * */
inline fun <reified T : Any> FragmentActivity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}

fun FragmentActivity.getCurrentFragment(@IdRes containerId: Int): Fragment? {
    return supportFragmentManager.findFragmentById(containerId)
}

fun FragmentActivity.setTransparentStatusBar(isDarkBackground: Boolean = false) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = if (isDarkBackground)
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        else
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun FragmentActivity.setStatusBarColor(@ColorRes color: Int, isDarkColor: Boolean = false) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window?.apply {
            decorView.systemUiVisibility = if (isDarkColor) 0 else View.SYSTEM_UI_FLAG_VISIBLE
            statusBarColor = ContextCompat.getColor(context, color)
        }
    }
}

fun FragmentActivity.openWithUrl(url: String) {
    val defaultBrowser =
        Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
    defaultBrowser.data = Uri.parse(url)
    this.startActivity(defaultBrowser)
}
