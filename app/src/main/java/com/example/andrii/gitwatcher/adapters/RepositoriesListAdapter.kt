package com.example.andrii.gitwatcher.adapters

import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.data.models.Repository
import com.example.andrii.gitwatcher.modules.reposytory.views.RepositoryActivity
import com.example.andrii.gitwatcher.tools.ImageTools
import kotlinx.android.synthetic.main.item_repository.view.*
import java.text.SimpleDateFormat
import java.util.*

class RepositoriesListAdapter : RecyclerView.Adapter<MyHolderRepositories>() {
    private val mRepositoriesList = ArrayList<Repository>()

    override fun onBindViewHolder(holder: MyHolderRepositories?, position: Int) {
        val repo = mRepositoriesList[position]
        holder!!.bindHolder(repo)
        holder.itemView.setOnClickListener{
            val intent = RepositoryActivity.newIntent(holder.itemView.context,repo)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolderRepositories {
        val inflatedView = parent!!.inflate(R.layout.item_repository, false)
        return MyHolderRepositories(inflatedView)
    }

    override fun getItemCount(): Int {
        return mRepositoriesList.size
    }

    fun addData(list:ArrayList<Repository>,isNewData:Boolean){
        if (isNewData) mRepositoriesList.clear()
        mRepositoriesList.addAll(list)
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

}

class MyHolderRepositories constructor(itemView:View): RecyclerView.ViewHolder(itemView),View.OnClickListener{

    private var view: View = itemView



    init {
        itemView.setOnClickListener(this)
    }

    fun bindHolder(repo:Repository) {
        view.name.text = repo.name
        view.stars.text = repo.stargazers_count
        view.share.text = repo.forks_count
        view.modificated.text = formatData(repo.updated_at)

        view.size.text = formatSize(repo.size)
        view.language.text = repo.language
        val color = getColorsMap()[repo.language]
        view.language.setTextColor(ContextCompat.getColor(view.context,color?:R.color.languageOther))

        val tools = ImageTools(view.context)
        tools.downloadUserImage(view.repoPhoto, repo.owner!!,false)
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

fun getColorsMap():HashMap<String,Int>{
    val map = HashMap<String,Int>()
    map["Kotlin"] = R.color.Kotlin
    map["Java"] = R.color.Java
    map["C#"] = R.color.DotNet
    map["C++"] = R.color.Cpp
    map["Swift"] = R.color.Swift
    map["JavaScript"] = R.color.JavaScript
    map["PHP"] = R.color.PHP

    return map
}

fun formatData(data: String?):String{
    val format = SimpleDateFormat("yyyy-MM-dd")
    val date = format.parse(data?.substring(0,10))
    val formatOut = SimpleDateFormat("dd MMM")
    return formatOut.format(date)
}

fun formatSize(size: String?):String{
    var s = size?.toDouble() ?: 0.0
    val textSize:String
    when{
        s <=1000 -> {textSize = "$size KB"}

        s in 1000..1000000 -> {
            s /= 1000
            textSize = "$s MB"
        }
        else -> {
            s /= 1000000
            textSize = "$size GB"
        }
    }
    return textSize.substring(0,3) + textSize.substring(textSize.length-3,textSize.length)
}