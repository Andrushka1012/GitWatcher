package com.example.andrii.gitwatcher.modules.StartPage.view

import android.os.Bundle
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.base.BaseView.BaseActivity
import com.example.andrii.gitwatcher.base.BaseView.BaseFragment
import com.example.andrii.gitwatcher.modules.StartPage.StartPagePresenter
import kotlinx.android.synthetic.main.app_bar_main.*

class StartPageActivity : BaseActivity(), StartPageView {

    private lateinit var presenter: StartPagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter = StartPagePresenter()
        presenter.onAttach(this)
        presenter.setUpViewpager(viewPager,supportFragmentManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun showProgress() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onFragmentAttached(fragment: BaseFragment) {
        presenter.onFragmentAttached(fragment)
    }

    override fun onFragmentDetached(fragment: BaseFragment) {
        presenter.onFragmentDetached(fragment)
    }

    override fun getStringResource(id: Int): String {
     return resources.getString(id)
    }

    fun getPresenter(): StartPagePresenter{
        return presenter
    }

}
