package com.example.andrii.gitwatcher.modules.search.view

import android.content.Context
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.adapters.ViewPagerAdapter
import com.example.andrii.gitwatcher.base.BaseView.BaseActivity
import com.example.andrii.gitwatcher.modules.search.SearchPresenter
import com.example.andrii.gitwatcher.modules.startPage.view.RecyclerViewFragment
import kotlinx.android.synthetic.main.activity_search.*
import rx.Observable
import rx.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class SearchActivity : BaseActivity() {

    private lateinit var presenter: SearchPresenter
    private lateinit var searchView:SearchView
    private lateinit var fragmentObservable: Observable<RecyclerViewFragment>
    private val subject:BehaviorSubject<String> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(searchToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        presenter = SearchPresenter()
        presenter.onAttach(this)

        setupViewPager()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.searchMenuItemSearch)
        searchView = searchItem.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        val params = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT)
        searchView.layoutParams = params
        searchItem.expandActionView()

        setTextListener(searchView)
        addRefreshListener(searchView)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        return when (id) {
            R.id.action_search -> {
                presenter.makeRequest((item as SearchView).query.toString(),fragmentObservable,retrofit)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showProgress() {
        searchRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        searchRefresh.isRefreshing = false
    }

    private fun setupViewPager() {
        val fragmentRepos = RecyclerViewFragment.newInstance(RecyclerViewFragment.TYPE_REPOS)
        val fragmentUsers = RecyclerViewFragment.newInstance(RecyclerViewFragment.TYPE_USERS)

        fragmentObservable = Observable.just(fragmentRepos, fragmentUsers)
                .doOnSubscribe{ showProgress() }
                .doOnTerminate{ hideProgress() }

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(fragmentRepos, getStringResource(R.string.ReposCount))
        viewPagerAdapter.addFragment(fragmentUsers, getStringResource(R.string.UsersCount))

        pager.adapter = viewPagerAdapter
        searchTabs.setupWithViewPager(pager, true)

    }

    fun changeTitle(type: Int, count: Int) {
        var title = searchTabs.getTabAt(type)!!.text
        title = if(title!!.startsWith("R",true)) getString(R.string.ReposCount) + "($count)"
        else getString(R.string.UsersCount) + "($count)"

        searchTabs.getTabAt(type)!!.text = title
    }

    fun loadMoreData(fragment:RecyclerViewFragment,page: Long) {
        presenter.loadMoreData(searchView.query.toString(),fragment,page,retrofit)
    }

    private fun addRefreshListener(searchView: SearchView) {
        searchRefresh.setOnRefreshListener { presenter.makeRequest(searchView.query.toString(),fragmentObservable,retrofit) }
    }

    private fun setTextListener(searchView: SearchView){
        searchView.setOnClickListener { presenter.makeRequest(searchView.query.toString(),fragmentObservable,retrofit) }
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //subject.onCompleted()
                if(currentFocus != null){
                    val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    manager.hideSoftInputFromInputMethod(currentFocus.windowToken,0)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText?.isEmpty()!!){
                    subject.onNext(newText)
                }
                return true
            }
        })

        val subscription = subject
                .debounce(600,TimeUnit.MILLISECONDS)
                .map { t -> (t as String).toLowerCase() }
                .subscribe({s:String -> presenter.makeRequest(s,fragmentObservable,retrofit)},
                        { Log.e("SearchActivity",it.toString()) })
        presenter.addSubscription(subscription)

    }




}
