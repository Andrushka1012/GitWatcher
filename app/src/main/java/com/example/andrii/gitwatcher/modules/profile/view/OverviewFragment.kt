package com.example.andrii.gitwatcher.modules.profile.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.adapters.OverviewOrganizationsListAdapter
import com.example.andrii.gitwatcher.data.Database.UserObjectDb
import com.example.andrii.gitwatcher.data.models.Organization
import com.example.andrii.gitwatcher.data.models.User
import com.example.andrii.gitwatcher.modules.profile.presenters.OverviewFragmentPresenter
import com.example.andrii.gitwatcher.modules.startPage.view.StartPageActivity
import com.example.andrii.gitwatcher.tools.ImageTools
import com.transitionseverywhere.ChangeText
import com.transitionseverywhere.TransitionManager
import kotlinx.android.synthetic.main.fragment_profile_overview.*
import java.text.SimpleDateFormat

class OverviewFragment:ProfileFragment(){

    private lateinit var login:String

    companion object {
        private const val FRAGMENT_LOGIN_ARGUMENT = "FRAGMENT_LOGIN_ARGUMENT"

        private lateinit var presenter:OverviewFragmentPresenter

        fun newInstance(login:String): OverviewFragment {
            val fragment = OverviewFragment()
            val bundle = Bundle()
            bundle.putString(FRAGMENT_LOGIN_ARGUMENT, login)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = OverviewFragmentPresenter()
        presenter.onAttach(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        login = arguments!!.getString(FRAGMENT_LOGIN_ARGUMENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_overview,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileFollowing.setOnClickListener { (getParentActivity() as ProfileActivity).scrollToPosition(2) }
        profileFollowers.setOnClickListener { (getParentActivity() as ProfileActivity).scrollToPosition(3) }

        setUI()
    }

    override fun onDetach() {
        super.onDetach()
        presenter.onDetach()
    }


    override fun setupData() {
        presenter.loadData(login,getParentActivity()!!.retrofit)
    }

    fun setUpUserData(user: User?){
        if (profileName == null){
            Toast.makeText(activity,"null", Toast.LENGTH_LONG).show()
            return
        }
        if(user != null){
            val realm = getParentActivity()!!.realm
            presenter.followUnfollowOnClick(realm,profileFollowUnfollowBtn,user)
            if (realm.where(UserObjectDb::class.java).equalTo("id",user.id).findFirst() != null){
                chaneButtonText()
            }
        }

        profileName.text = user?.name ?: ""
        profileName.text = user?.name ?: ""
        profileLogin.text = user?.login ?: ""
        profileBio.text = user?.bio ?: ""
        profileFollowing.text = activity.getString(R.string.following) + "(${user?.following ?: 0})"
        profileFollowers.text = activity.getString(R.string.followers) + "(${user?.followers ?: 0})"

        if(user?.company == null || user.company =="null") profileOrganisation.visibility = (View.GONE)
        else profileOrganisationTv.text = user.company

        if(user?.location == null || user.location =="null") profileLocation.visibility = (View.GONE)
        else profileLocationTv.text = user.location

        if(user?.blog == null || user.blog =="null" || user.blog!!.isEmpty()) profileBlog.visibility = (View.GONE)
        else profileBlogTv.text = user.blog

        if(user?.updated_at == null || user.updated_at =="null") profileUpdated.visibility = (View.GONE)
        else{
            val format = SimpleDateFormat("yyyy-MM-dd")
            val date = format.parse(user.updated_at!!.substring(0,10))
            val formatOut = SimpleDateFormat("dd MMM yyyy")
            val d = formatOut.format(date)
            profileUpdatedTv.text = d
        }
        if(user?.avatar_url != null) {
            val tools = ImageTools(activity)
            tools.downloadUserImage(overviewPhoto,user,false)
        }

    }

    fun setOrganizations(list: ArrayList<Organization>?) {
        if (list == null || list.isEmpty())  overviewOrganizations.setVerticalGravity(View.GONE)
        else{
            profileOrgRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            profileOrgRecyclerView.adapter = OverviewOrganizationsListAdapter(list)
        }
    }

    fun chaneButtonText(){
        TransitionManager.beginDelayedTransition(overviewContainer,
                ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN))
        profileFollowUnfollowBtn.text = when(profileFollowUnfollowBtn.text){
            getParentActivity()?.getString(R.string.follow) ->{getParentActivity()?.getString(R.string.unfollow)}
            getParentActivity()?.getString(R.string.unfollow) ->{getParentActivity()?.getString(R.string.follow)}
            else -> ""
        }
    }

    fun followingStateChanged(){ StartPageActivity.dataChanged = true}

    private fun setUI(){
        val user = presenter.provideUser()
        val organizations = presenter.provideOrganisations()

        if (user == null || organizations == null)setupData()
        else {
            setUpUserData(user)
            setOrganizations(organizations)
        }
    }

    override fun showProgress() {
        //do nothing
    }

    override fun hideProgress() {
        //do nothing
    }

}
