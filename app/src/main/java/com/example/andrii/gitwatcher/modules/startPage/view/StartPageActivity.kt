package com.example.andrii.gitwatcher.modules.startPage.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.adapters.ViewPagerAdapter
import com.example.andrii.gitwatcher.base.BaseView.BaseActivity
import com.example.andrii.gitwatcher.modules.startPage.StartPagePresenter
import kotlinx.android.synthetic.main.app_bar_main.*
import rx.Observable


class StartPageActivity : BaseActivity(), StartView {

    companion object {
        var dataChanged:Boolean = false
    }

    private lateinit var presenter: StartPagePresenter
    private lateinit var fragmentObservable: Observable<RecyclerViewFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        presenter = StartPagePresenter()
        presenter.onAttach(this)

        setupViewPager()
        addRefreshListener()
        addOffsetChangedListener()
    }

    override fun onResume() {
        super.onResume()
        if(dataChanged){
            dataChanged = false
            update()
        }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        return when (id) {
            R.id.action_search -> { presenter.onSearchItemSelected() }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewPager(){

        val fragmentRepos = RecyclerViewFragment.newInstance(RecyclerViewFragment.TYPE_REPOS)
        val fragmentUsers = RecyclerViewFragment.newInstance(RecyclerViewFragment.TYPE_USERS)

        fragmentObservable = Observable.just(fragmentRepos,fragmentUsers)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(fragmentRepos, getStringResource(R.string.ReposCount))
        viewPagerAdapter.addFragment(fragmentUsers,getStringResource(R.string.UsersCount))

        viewPager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(viewPager,true)

    }

    override fun setUpRecyclerView(fragment: RecyclerViewFragment, type: Int){
        when(type){
            RecyclerViewFragment.TYPE_REPOS ->{
                val list = presenter.provideRepositoriesData(realm)
                fragment.setUpRecyclerViewRepos(list,getStringResource(R.string.EmptyDbRepo),true)
                changeTitle(type,list.size)
            }
            RecyclerViewFragment.TYPE_USERS ->{
                val list = presenter.provideUsersData(realm)
                fragment.setUpRecyclerViewUsers(list,getStringResource(R.string.EmptyDbUsers),true)
                changeTitle(type,list.size)
            }
        }
    }

    private fun changeTitle(type: Int, count: Int) {
        var title = tabs.getTabAt(type)!!.text
        title = if(title!!.startsWith("R",true)) getString(R.string.ReposCount) + "($count)"
        else getString(R.string.UsersCount) + "($count)"

        tabs.getTabAt(type)!!.text = title
    }

    private fun addRefreshListener() {
     swipeRefreshLayout.setOnRefreshListener { this.update() }
    }

    private fun addOffsetChangedListener() {
        appBar.addOnOffsetChangedListener({ _, i ->
            swipeRefreshLayout.isEnabled = i == 0
        })
    }

    private fun update(){
        fragmentObservable.doOnSubscribe(this::showProgress)
                .doOnTerminate(this::hideProgress)
                .subscribe{fragment: RecyclerViewFragment? -> setUpRecyclerView(fragment!!,fragment.getType()) }
    }

}
