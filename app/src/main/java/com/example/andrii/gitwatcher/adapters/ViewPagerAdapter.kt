package com.example.andrii.gitwatcher.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter constructor(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    private val mFragments = ArrayList<Fragment>()
    private val mTitles = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }

    fun addFragment(fragment:Fragment,title:String) {
        mFragments+=fragment
        mTitles+=title
    }
}
