package com.example.andrii.gitwatcher.modules.profile.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.adapters.ViewPagerAdapter
import com.example.andrii.gitwatcher.base.BaseView.BaseActivity
import kotlinx.android.synthetic.main.activity_profile.*



class ProfileActivity:BaseActivity(){

    private var presenter: ProfilePresenter? = null
    private lateinit var login: String

    companion object {
        const val LOGIN_EXTRA = "LOGIN_EXTRA"
        fun newIntent(con: Context,login:String):Intent{
            val intent = Intent(con,ProfileActivity::class.java)
            intent.putExtra(LOGIN_EXTRA,login)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupPresenter(savedInstanceState)
        setupActionBat()
    }

    override fun onStart() {
        super.onStart()
        setupViewPager()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        presenter?.saveInstances(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showProgress() {
        profilePager.visibility = View.GONE
        profileProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        profilePager.visibility = View.VISIBLE
        profileProgressBar.visibility = View.GONE
    }

    fun getPresenter() = presenter

    private fun setupActionBat() {
        setSupportActionBar(profileToolbar)
        login = intent.getStringExtra(LOGIN_EXTRA)
        supportActionBar!!.title = login
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    private fun setupPresenter(savedInstanceState: Bundle?) {
        presenter = ProfilePresenter()
        presenter?.onAttach(this)
        presenter?.loadInstances(savedInstanceState)
    }

    fun scrollToPosition(position:Int){
        profileTabs.isSmoothScrollingEnabled = true
        profileTabs.setScrollPosition(position,0f,false)
        profilePager.setCurrentItem(position,true)
    }

    private fun setupViewPager() {
        val fragmentOverview = OverviewFragment.newInstance(login)
        val fragmentRepositories = ProfileRepositoriesFragment.newInstance(login)
        val fragmentFollowing = FollowerFollowingFragment.newInstance(FollowerFollowingFragment.TYPE_FOLLOWING,login)
        val fragmentFollower = FollowerFollowingFragment.newInstance(FollowerFollowingFragment.TYPE_FOLLOWER,login)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(fragmentOverview, getStringResource(R.string.overview))
        viewPagerAdapter.addFragment(fragmentRepositories, getStringResource(R.string.ReposCount))
        viewPagerAdapter.addFragment(fragmentFollowing, getStringResource(R.string.following))
        viewPagerAdapter.addFragment(fragmentFollower, getStringResource(R.string.followers))

        profilePager.adapter = viewPagerAdapter
        profileTabs.setupWithViewPager(profilePager,true)
    }


}
