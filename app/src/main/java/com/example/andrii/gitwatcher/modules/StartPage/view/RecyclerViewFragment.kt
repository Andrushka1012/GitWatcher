package com.example.andrii.gitwatcher.modules.StartPage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.base.BaseView.BaseFragment


class RecyclerViewFragment : BaseFragment() {

    private var type: Int = 0

    companion object {
       val TYPE_REPOS = 1
       val TYPE_USERS = 2
       val FRAGMENT_TYPE_ARGUMENT = "FRAGMENT_TYPE_ARGUMENT"

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
        type = arguments.getInt(FRAGMENT_TYPE_ARGUMENT)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_recyclerview,container,false)
        initializeRecyclerView()
        return view
    }

    override fun showProgress() {
        getParentActivity()?.hideProgress()
    }

    override fun hideProgress() {
        getParentActivity()?.hideProgress()
    }

    fun getType() = type

    private fun initializeRecyclerView(){
        val presenter = (getParentActivity() as StartPageActivity).getPresenter()
        presenter.initializeRecyclerView(this)
    }

    fun setUpRecyclerView(list: List<Any>){

    }


}
