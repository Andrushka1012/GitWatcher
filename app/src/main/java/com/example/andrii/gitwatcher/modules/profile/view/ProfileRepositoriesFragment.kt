package com.example.andrii.gitwatcher.modules.profile.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.adapters.RepositoriesListAdapter
import com.example.andrii.gitwatcher.data.models.Repository
import com.example.andrii.gitwatcher.modules.profile.presenters.ProfileRepositoriesPresenter
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class ProfileRepositoriesFragment:ProfileFragment(){

    private lateinit var presenter: ProfileRepositoriesPresenter
    private var type: Int = 0
    private lateinit var login: String
    fun getType() = type

    companion object {
        const val FRAGMENT_LOGIN_ARGUMENT = "FRAGMENT_LOGIN_ARGUMENT"

        fun newInstance(login:String): ProfileRepositoriesFragment {
            val fragment = ProfileRepositoriesFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENT_LOGIN_ARGUMENT,login)
            fragment.arguments = bundle

            return fragment
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = ProfileRepositoriesPresenter()
        presenter.onAttach(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        login = arguments!!.getString(FRAGMENT_LOGIN_ARGUMENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recyclerview,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentRecyclerViewRefresh.isEnabled = false

        setupUI()
    }

    override fun onDetach() {
        super.onDetach()
        presenter.onDetach()
    }


    override fun setupData() {
     presenter.setupData(login,getParentActivity()!!.retrofit)
    }

    fun setUpRecyclerView(list: ArrayList<Repository>?){
        if (recyclerView == null){
            Toast.makeText(activity,"null", Toast.LENGTH_LONG).show()
            return
        }
        when(list?.size){
            0 ->{
                recyclerView.visibility = View.GONE
                tvInfo.visibility = View.VISIBLE
                tvInfo.text = getString(R.string.noRepos)
            }
            null ->{
                recyclerView.visibility = View.GONE
                tvInfo.visibility = View.VISIBLE
                tvInfo.text = getParentActivity()!!.getStringResource(R.string.SomeError)
            }
            else ->{
                recyclerView.visibility = View.VISIBLE
                tvInfo.visibility = View.GONE
                recyclerView.layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = RepositoriesListAdapter()
                (recyclerView.adapter as RepositoriesListAdapter).addData(list,true)

                fragmentRecyclerViewRefresh.isRefreshing = false
            }
        }
    }

    private fun setupUI() {
        val repos = presenter.provideRepositories()
        if(repos == null ) setupData()
        else setUpRecyclerView(repos)
    }

    override fun showProgress() {
        //do nothing
    }

    override fun hideProgress() {
        //do nothing
    }

}