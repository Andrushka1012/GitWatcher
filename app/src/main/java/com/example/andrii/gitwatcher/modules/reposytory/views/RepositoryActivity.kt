package com.example.andrii.gitwatcher.modules.reposytory.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.adapters.ViewPagerAdapter
import com.example.andrii.gitwatcher.base.BaseView.BaseActivity
import kotlinx.android.synthetic.main.activity_repo.*



class RepositoryActivity:BaseActivity(){

    private lateinit var userLogin: String
    private lateinit var repoLogin: String

    companion object {
        const val USER_LOGIN_EXTRA = "USER_LOGIN_EXTRA"
        const val REPO_LOGIN_EXTRA = "REPO_LOGIN_EXTRA"
        fun newIntent(con: Context, userLogin:String,repoLogin:String): Intent {
            val intent = Intent(con, RepositoryActivity::class.java)
            intent.putExtra(USER_LOGIN_EXTRA, userLogin)
            intent.putExtra(REPO_LOGIN_EXTRA, repoLogin)

            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        setSupportActionBar(repoToolbar)

        userLogin = intent.getStringExtra(USER_LOGIN_EXTRA)
        repoLogin = intent.getStringExtra(REPO_LOGIN_EXTRA)

        setToolbar()
        setupViewPager()
    }

    private fun setToolbar() {
        supportActionBar!!.title = userLogin
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        repoToolbar.setNavigationOnClickListener { _ -> onBackPressed() }
    }

    private fun setupViewPager() {
        val fragmentReadme = ReadmeFragment.newInstance(userLogin,repoLogin)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(fragmentReadme,getString(R.string.readme))

        repoPager.adapter = viewPagerAdapter
        repoTabs.setupWithViewPager(repoPager)
    }


    override fun showProgress() {}
    override fun hideProgress() {}

}
