package com.example.andrii.gitwatcher.modules.StartPage


import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.adapters.ViewPagerAdapter
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.base.BaseView.BaseActivity
import com.example.andrii.gitwatcher.base.BaseView.BaseFragment
import com.example.andrii.gitwatcher.data.models.Repository
import com.example.andrii.gitwatcher.modules.StartPage.view.RecyclerViewFragment
import com.example.andrii.gitwatcher.modules.StartPage.view.StartPageView
import timber.log.Timber

class StartPagePresenter : BasePresenter<StartPageView>() {

    private lateinit var viewPagerAdapter:ViewPagerAdapter
    private val fragmentList = ArrayList<BaseFragment>()

    fun setUpViewpager(viewPager: ViewPager,fragmentManager: FragmentManager) {
        val fragmentRepos = RecyclerViewFragment.newInstance(RecyclerViewFragment.TYPE_REPOS)
        val fragmentUsers = RecyclerViewFragment.newInstance(RecyclerViewFragment.TYPE_USERS)

        viewPagerAdapter = ViewPagerAdapter(fragmentManager)
        viewPagerAdapter.addFragment(fragmentUsers, getView()!!.getStringResource(R.string.ReposCount))
        viewPagerAdapter.addFragment(fragmentRepos,getView()!!.getStringResource(R.string.UsersCount))

        viewPager.adapter = viewPagerAdapter
    }

    fun onFragmentAttached(fragment: BaseFragment){
        fragmentList+=fragment

    }
    fun onFragmentDetached(fragment: BaseFragment){
        fragmentList.remove(fragment)
    }

    fun initializeRecyclerView(fragment: RecyclerViewFragment){
        val type = fragment.getType()
        val realm = (getView() as BaseActivity).realm
        when(type){
            RecyclerViewFragment.TYPE_REPOS ->{
                fragment.setUpRecyclerView(Repository.getAllRepositoriesFromRealm(realm))
            }
            RecyclerViewFragment.TYPE_USERS ->{
                fragment.setUpRecyclerView(Repository.getAllRepositoriesFromRealm(realm))
            }
            else ->{
                Timber.d(Throwable("Unexpected fragment type"))
            }
        }

    }

}
