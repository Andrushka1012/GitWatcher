package com.example.andrii.gitwatcher.modules.startPage.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.adapters.RepositoriesListAdapter
import com.example.andrii.gitwatcher.adapters.UsersListAdapter
import com.example.andrii.gitwatcher.base.BaseView.BaseFragment
import com.example.andrii.gitwatcher.data.models.Repository
import com.example.andrii.gitwatcher.data.models.User
import com.example.andrii.gitwatcher.modules.search.view.SearchActivity
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class RecyclerViewFragment : BaseFragment() {

    private var type: Int = 0
    private var page:Long = 1
    fun getType() = type

    companion object {
       const val TYPE_REPOS = 0
       const val TYPE_USERS = 1
       const val FRAGMENT_TYPE_ARGUMENT = "FRAGMENT_TYPE_ARGUMENT"

        fun newInstance(type:Int): RecyclerViewFragment {
            val fragment = RecyclerViewFragment()
            val bundle = Bundle()
            bundle.putInt(FRAGMENT_TYPE_ARGUMENT, type)
            fragment.arguments = bundle

            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        type = arguments!!.getInt(FRAGMENT_TYPE_ARGUMENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recyclerview,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
        if (getParentActivity() is StartView){
            fragmentRecyclerViewRefresh.isEnabled = false
            (getParentActivity() as StartView).setUpRecyclerView(this,type)
        }
        else fragmentRecyclerViewRefresh.setOnRefreshListener { loadMoreData() }
    }


    override fun showProgress() {
        getParentActivity()?.hideProgress()
    }

    override fun hideProgress() {
        getParentActivity()?.hideProgress()
    }

    private fun setUpRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter =  when(type) {
                TYPE_REPOS ->{ RepositoriesListAdapter() }
                TYPE_USERS -> {UsersListAdapter(activity) }
                else -> null
        }
    }

    private fun loadMoreData() {
        (getParentActivity() as SearchActivity).loadMoreData(this,++page)
    }

    fun reset(){ page = 1 }

    fun setUpRecyclerViewRepos(list: ArrayList<Repository>?,emptyMessage:String,isNewData:Boolean) {
        when(list?.size){
            0 ->{
                recyclerView.visibility = View.GONE
                tvInfo.visibility = View.VISIBLE
                tvInfo.text = emptyMessage
            }
            null ->{
                recyclerView.visibility = View.GONE
                tvInfo.visibility = View.VISIBLE
                tvInfo.text = getParentActivity()!!.getStringResource(R.string.SomeError)
            }
            else ->{
                recyclerView.visibility = View.VISIBLE
                tvInfo.visibility = View.GONE
                (recyclerView.adapter as RepositoriesListAdapter).addData(list,isNewData)
                recyclerView.adapter.notifyDataSetChanged()
                if (isNewData) recyclerView.scrollToPosition(0)

                fragmentRecyclerViewRefresh.isRefreshing = false
            }
        }
    }

    fun setUpRecyclerViewUsers(list: ArrayList<User>?,emptyMessage:String,isNewData:Boolean) {

        when(list?.size){
            0 ->{
                recyclerView.visibility = View.GONE
                tvInfo.visibility = View.VISIBLE
                tvInfo.text = emptyMessage
            }
            null ->{
                recyclerView.visibility = View.GONE
                tvInfo.visibility = View.VISIBLE
                tvInfo.text = getParentActivity()!!.getStringResource(R.string.SomeError)
            }
            else ->{
                recyclerView.visibility = View.VISIBLE
                tvInfo.visibility = View.GONE
                (recyclerView.adapter as UsersListAdapter).addData(list,isNewData)
                recyclerView.adapter.notifyDataSetChanged()
                if (isNewData) recyclerView.scrollToPosition(0)

                fragmentRecyclerViewRefresh.isRefreshing = false
            }
        }
    }

}
