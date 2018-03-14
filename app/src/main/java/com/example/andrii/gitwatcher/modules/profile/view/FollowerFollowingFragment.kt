package com.example.andrii.gitwatcher.modules.profile.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.adapters.UsersListAdapter
import com.example.andrii.gitwatcher.data.models.User
import com.example.andrii.gitwatcher.modules.profile.presenters.FollowerFollowingPresenter
import com.example.andrii.gitwatcher.modules.startPage.view.RecyclerViewFragment
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class FollowerFollowingFragment:ProfileFragment(){
    private lateinit var presenter:FollowerFollowingPresenter
    private var type: Int = 0
    private lateinit var login: String
    fun getType() = type

    companion object {
        private const val FRAGMENT_TYPE_ARGUMENT = "FRAGMENT_TYPE_ARGUMENT"
        const val TYPE_FOLLOWER = 0
        const val TYPE_FOLLOWING = 1
        const val FRAGMENT_LOGIN_ARGUMENT = "FRAGMENT_LOGIN_ARGUMENT"

        fun newInstance(type:Int,login:String): FollowerFollowingFragment {
            val fragment = FollowerFollowingFragment()
            val bundle = Bundle()
            bundle.putInt(FRAGMENT_TYPE_ARGUMENT, type)
            bundle.putString(FRAGMENT_LOGIN_ARGUMENT,login)
            fragment.arguments = bundle

            return fragment
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = FollowerFollowingPresenter()
        presenter.onAttach(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments!!.getInt(RecyclerViewFragment.FRAGMENT_TYPE_ARGUMENT)
        login = arguments!!.getString(FRAGMENT_LOGIN_ARGUMENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recyclerview,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentRecyclerViewRefresh.isEnabled = false

        setupUi()
    }

    override fun onDetach() {
        super.onDetach()
        presenter.onDetach()
    }

    override fun setupData() {
     presenter.setupData(getParentActivity()!!.retrofit,login)
    }

    fun setUpRecyclerView(list: ArrayList<User>?){
        if (recyclerView == null){
            Toast.makeText(activity,"null",Toast.LENGTH_LONG).show()
            return
        }
        when(list?.size){
            0 ->{
                recyclerView.visibility = View.GONE
                tvInfo.visibility = View.VISIBLE
                tvInfo.text = when(type){
                    TYPE_FOLLOWER -> getString(R.string.noFollower)
                    TYPE_FOLLOWING -> getString(R.string.noFollowing)
                    else -> ""
                }
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
                recyclerView.adapter = UsersListAdapter(activity)
                (recyclerView.adapter as UsersListAdapter).addData(list,true)
            }
        }
    }

    private fun setupUi() {
        val list:ArrayList<User>? = when(type){
            TYPE_FOLLOWING -> presenter.provideFollowing()
            TYPE_FOLLOWER -> presenter.provideFollowers()
            else -> null
        }
        if (list == null) setupData()
        else setUpRecyclerView(list)

    }

    override fun showProgress() {
        //do nothing
    }

    override fun hideProgress() {
        //do nothing
    }

}
