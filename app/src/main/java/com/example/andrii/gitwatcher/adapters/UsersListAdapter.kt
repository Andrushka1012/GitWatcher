package com.example.andrii.gitwatcher.adapters

import android.app.Activity
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.data.models.User
import com.example.andrii.gitwatcher.modules.profile.view.ProfileActivity
import com.example.andrii.gitwatcher.tools.ImageTools
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.*

class UsersListAdapter constructor(val contex:Activity): RecyclerView.Adapter<MyHolder>() {
    val mUserList = ArrayList<User>()

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        val user = mUserList[position]
        holder!!.bindPhoto(user)
        holder.itemView.setOnClickListener{
            val intent = ProfileActivity.newIntent(holder.itemView.context,user.login!!)
           /* var bundle:Bundle? = null
            val context = when{
                holder.itemView.context is SearchActivity -> {holder.itemView.context as SearchActivity}
                holder.itemView.context is StartPageActivity -> {holder.itemView.context as StartPageActivity}
                else -> null
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bundle = ActivityOptions
                            .makeSceneTransitionAnimation(context,
                                    holder.itemView.photo,context?.getString(R.string.userPhoto)).toBundle()
                }

            if (bundle == null) holder.itemView.context.startActivity(intent)
            else holder.itemView.context.startActivity(intent,bundle)
            */
//            (holder.itemView.context as Activity).startActivityForResult(intent,StartPageActivity.START_PAGE_DATA_CHANGED_REQUEST)
            contex.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        val inflatedView = parent!!.inflate(R.layout.item_user, false)
        return MyHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return mUserList.size
    }

    fun addData(list:ArrayList<User>,isNewData:Boolean){
        if(isNewData) mUserList.clear()
        mUserList.addAll(list)
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }



}

class MyHolder constructor(itemView:View): RecyclerView.ViewHolder(itemView){

    private var view: View = itemView

    fun bindPhoto(user:User) {
        view.name.text = user.login
        val tools = ImageTools(view.context)
        tools.downloadUserImage(view.photo,user,false)
    }



}