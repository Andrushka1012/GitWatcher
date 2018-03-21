package com.example.andrii.gitwatcher.modules.reposytory.views

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.adapters.DirectoryListAdapter
import com.example.andrii.gitwatcher.adapters.FilesListAdapter
import com.example.andrii.gitwatcher.base.BaseView.BaseFragment
import com.example.andrii.gitwatcher.data.models.FileModel
import com.example.andrii.gitwatcher.modules.reposytory.presenters.FilesPresenter
import kotlinx.android.synthetic.main.fragment_files.*

class FilesFragment: BaseFragment() {

    companion object {
        private const val OWNER_LOGIN_ARGUMENT = "OWNER_LOGIN_ARGUMENT"
        private const val REPO_NAME_ARGUMENT = "REPO_NAME_ARGUMENT"

        fun newInstance(userName:String,repoName:String):FilesFragment{
            val fragment = FilesFragment()
            val bundle = Bundle()
            bundle.putString(OWNER_LOGIN_ARGUMENT,userName)
            bundle.putString(REPO_NAME_ARGUMENT,repoName)

            fragment.arguments = bundle

            return fragment
        }
    }

    lateinit var ownerName:String
    lateinit var repoName:String
    lateinit var presenter:FilesPresenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = FilesPresenter()
        presenter.onAttach(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ownerName = arguments.getString(OWNER_LOGIN_ARGUMENT)
        repoName = arguments.getString(REPO_NAME_ARGUMENT)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_files,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onDetach() {
        super.onDetach()
        presenter.onDetach()
    }

    override fun showProgress() {
        filesProgress.visibility = View.VISIBLE
        recyclerViewFiles.visibility = View.GONE
    }

    override fun hideProgress() {
        filesProgress.visibility = View.GONE
        recyclerViewFiles.visibility = View.VISIBLE
    }

    fun onBackPressed() {
        (recyclerViewDirectory.adapter as DirectoryListAdapter).onBackPressed()
    }

    fun goToDir(fileModel: FileModel) {
        (recyclerViewDirectory.adapter as DirectoryListAdapter).addDirectory(fileModel.name ?: "")
    }

    fun setFiles(files: ArrayList<FileModel>?) {
        recyclerViewFiles.adapter = FilesListAdapter(presenter,files ?: ArrayList())
        recyclerViewDirectory.adapter.notifyDataSetChanged()
        recyclerViewDirectory.scrollToPosition(recyclerViewDirectory.adapter.itemCount -1)
    }

    fun getDirItemCount() = recyclerViewDirectory.adapter.itemCount

    private fun setupRecyclerView() {
        recyclerViewDirectory.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        recyclerViewFiles.layoutManager = LinearLayoutManager(activity)
        val list = ArrayList<String>()
        list.add("home")
        recyclerViewDirectory.adapter = DirectoryListAdapter(presenter,list)
        presenter.goToDir("")
    }

}
