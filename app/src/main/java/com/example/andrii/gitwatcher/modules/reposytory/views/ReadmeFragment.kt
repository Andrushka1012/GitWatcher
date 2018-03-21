package com.example.andrii.gitwatcher.modules.reposytory.views

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.base.BaseView.BaseFragment
import com.example.andrii.gitwatcher.modules.reposytory.presenters.ReadmePresenter
import kotlinx.android.synthetic.main.fragment_readme.*

class ReadmeFragment:BaseFragment(){

    private lateinit var repoLogin:String
    private lateinit var userLogin:String
    companion object {
        private const val FRAGMENT_USER_LOGIN_ARGUMENT = "FRAGMENT_USER_LOGIN_ARGUMENT"
        private const val FRAGMENT_REPO_LOGIN_ARGUMENT = "FRAGMENT_REPO_LOGIN_ARGUMENT"
        private lateinit var presenter: ReadmePresenter

        fun newInstance(userLogin:String,repoLogin:String): ReadmeFragment {
            val fragment = ReadmeFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENT_REPO_LOGIN_ARGUMENT, repoLogin)
            bundle.putString(FRAGMENT_USER_LOGIN_ARGUMENT, userLogin)

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = ReadmePresenter()
        presenter.onAttach(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        repoLogin = arguments!!.getString(FRAGMENT_REPO_LOGIN_ARGUMENT)
        userLogin = arguments!!.getString(FRAGMENT_USER_LOGIN_ARGUMENT)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_readme,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setUpData(userLogin,repoLogin)
    }

    override fun showProgress() {

    }

    override fun hideProgress() {
    }

    fun showReadme(markdown: String?) {
        var text = markdown
        if (text == null || text.isEmpty()) text =  "**Not found**"

        md_view.setMDText(text)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            md_view.background = activity.getDrawable(R.color.colorBackground)
        }
        md_view.setCodeScrollDisable()
    }

}