package com.example.andrii.gitwatcher.base.BaseView

import android.content.Context
import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment(), MVPView{
    private var parentActivity: BaseActivity? = null

    fun getParentActivity() = parentActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.parentActivity = activity
            parentActivity?.onFragmentAttached(this)
        }
    }

    override fun onDetach() {
        super.onDetach()
        parentActivity?.onFragmentAttached(this)
    }

    interface CallBack {
        fun onFragmentAttached(fragment: BaseFragment)
        fun onFragmentDetached(fragment: BaseFragment)
    }
}