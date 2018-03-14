package com.example.andrii.gitwatcher.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.data.models.Organization
import com.example.andrii.gitwatcher.tools.ImageTools
import kotlinx.android.synthetic.main.irem_organization_recycler_view.view.*

class OverviewOrganizationsListAdapter constructor(private val mOrganizationsList:ArrayList<Organization>)
    :RecyclerView.Adapter<MyHolderHorizontalOrganizations>() {

    override fun onBindViewHolder(holder: MyHolderHorizontalOrganizations?, position: Int) {
        holder!!.bindHolder(mOrganizationsList[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolderHorizontalOrganizations {
        val inflatedView = parent!!.inflate(R.layout.irem_organization_recycler_view, false)
        return MyHolderHorizontalOrganizations(inflatedView)
    }

    override fun getItemCount(): Int {
        return mOrganizationsList.size
    }


    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

}

class MyHolderHorizontalOrganizations constructor(itemView: View): RecyclerView.ViewHolder(itemView){

    private var view: View = itemView

    fun bindHolder(org: Organization) {
        view.itemOrgName.text = org.login
        val tools = ImageTools(view.context)
        tools.downloadImage(org.avatar_url.toString(),view.itemOrgPhoto,true)
    }


}

