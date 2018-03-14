package com.example.andrii.gitwatcher.modules.search

import android.util.Log
import android.widget.Toast
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.GItHubAPI.GitHubClient
import com.example.andrii.gitwatcher.data.models.SearchRepisotory
import com.example.andrii.gitwatcher.data.models.SearchUsers
import com.example.andrii.gitwatcher.modules.search.view.SearchActivity
import com.example.andrii.gitwatcher.modules.startPage.view.RecyclerViewFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers


class SearchPresenter: BasePresenter<SearchActivity>(){
    private val subscriptions:ArrayList<Subscription> = ArrayList()

    override fun onDetach() {
        super.onDetach()
        subscriptions.forEach{ if (!it.isUnsubscribed) it.unsubscribe() }
    }

    private fun provideRepositoriesData(str: String, fragment: RecyclerViewFragment,page:Long, retrofit:Retrofit,isNewData:Boolean) {
        val client = retrofit.create(GitHubClient::class.java)
        val call = client.searchRepositories(str,page)

        call.enqueue(object : Callback<SearchRepisotory> {
            override fun onFailure(call: Call<SearchRepisotory>?, t: Throwable?) {
                Log.e("SearchPresenter",t.toString())
                Toast.makeText(getView(),"Connection error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<SearchRepisotory>?, response: Response<SearchRepisotory>?) {
                val repos = response?.body()
                fragment.setUpRecyclerViewRepos(
                        repos?.items, getView()!!.getStringResource(R.string.EmptySearch),isNewData)
                changeTitle(fragment.getType(), repos?.total_count ?: 0)
            }
        })
    }

    private fun provideUsersData(str: String, fragment: RecyclerViewFragment,page:Long, retrofit:Retrofit,isNewData:Boolean){
        val client = retrofit.create(GitHubClient::class.java)
        val call = client.searchUser(str,page)

        call.enqueue(object : Callback<SearchUsers> {
            override fun onFailure(call: Call<SearchUsers>?, t: Throwable?) {
                Log.e("SearchPresenter",t.toString())
                Toast.makeText(getView(),"Connection error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<SearchUsers>?, response: Response<SearchUsers>?) {
                val repos = response?.body()
                fragment.setUpRecyclerViewUsers(
                        repos?.items, getView()!!.getStringResource(R.string.EmptySearch),isNewData)
                changeTitle(fragment.getType(), repos?.total_count ?: 0)
            }
        })

    }

    fun makeRequest(str:String,fragmentObservable: Observable<RecyclerViewFragment>,retrofit:Retrofit){
        val subscription = fragmentObservable
                .doOnSubscribe{ getView()!!.showProgress()}
                .doOnTerminate { getView()!!.hideProgress() }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.reset()
                    if (it.getType() == RecyclerViewFragment.TYPE_REPOS) {
                        provideRepositoriesData(str,it,1,retrofit,true)
                    } else provideUsersData(str,it,1,retrofit,true) }
                        ,{ Log.e("pizdec","make req",it)} )
        subscriptions.add(subscription)
    }

    fun addSubscription(sub:Subscription){ subscriptions.add(sub)}

    private fun changeTitle(type: Int, count: Int) { (getView() as SearchActivity).changeTitle(type,count) }

    fun loadMoreData(query: String, fragment: RecyclerViewFragment, page: Long, retrofit: Retrofit) {
        when(fragment.getType()){
            RecyclerViewFragment.TYPE_REPOS -> { provideRepositoriesData(query,fragment,page,retrofit,false) }
            RecyclerViewFragment.TYPE_USERS -> { provideUsersData(query,fragment,page,retrofit,false) }
        }
    }


}
