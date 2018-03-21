package com.example.andrii.gitwatcher.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.data.models.FileModel
import com.example.andrii.gitwatcher.modules.reposytory.presenters.FilesPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_file.view.*

class FilesListAdapter constructor(private val parentPresenter:FilesPresenter, private val mFiles:ArrayList<FileModel>)
    :RecyclerView.Adapter<FilesListAdapter.MyHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        val inflatedView = parent!!.inflate(R.layout.item_file, false)
        return MyHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return mFiles.size
    }

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        val file = mFiles[position]
        holder?.bindHolder(file)
        holder?.itemView?.setOnClickListener { parentPresenter.fileClicked(file) }
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    class MyHolder constructor(itemView:View):RecyclerView.ViewHolder(itemView){
        private var view: View = itemView

        fun bindHolder(file:FileModel){
            val iconId = when(file.type){
                "dir" -> R.drawable.ic_action_dir
                "file" -> R.drawable.ic_action_file
                else -> R.drawable.ic_action_error
            }

            Picasso.with(view.context)
                    .load(iconId)
                    .error(R.drawable.ic_action_error)
                    .into(view.fileItemIcon)

            view.fileItemName.text = file.name
        }
    }
}
