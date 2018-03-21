package com.example.andrii.gitwatcher.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.modules.reposytory.presenters.FilesPresenter
import kotlinx.android.synthetic.main.item_directory.view.*

class DirectoryListAdapter constructor(private val parentPresenter: FilesPresenter, private var mFiles:ArrayList<String>)
    : RecyclerView.Adapter<DirectoryListAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        val inflatedView = parent!!.inflate(R.layout.item_directory, false)
        return MyHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return mFiles.size
    }

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        holder?.bindHolder(mFiles[position])
        holder?.itemView?.setOnClickListener { goToDir(position) }
    }

    fun addDirectory(name: String) {
        mFiles.add(name)
        goToDir(mFiles.size -1)
    }

    fun onBackPressed() {
        goToDir(mFiles.size - 2)
    }

    private fun goToDir(position: Int) {
        var path = ""
        val list = ArrayList<String>()
        list.add("home")
        for (i:Int in 1..position step 1){
            val fileName = mFiles[i]
            path += "/$fileName"
            list.add(fileName)
        }
        mFiles = list

        parentPresenter.goToDir(path)
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }


    class MyHolder constructor(itemView: View):RecyclerView.ViewHolder(itemView){
        private var view: View = itemView

        fun bindHolder(fileName:String){
            view.itemDirectoryName.text = fileName
        }
    }

}
