package com.example.andrii.gitwatcher.modules.reposytory.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.adapters.ViewPagerAdapter
import com.example.andrii.gitwatcher.adapters.formatData
import com.example.andrii.gitwatcher.adapters.formatSize
import com.example.andrii.gitwatcher.adapters.getColorsMap
import com.example.andrii.gitwatcher.base.BaseView.BaseActivity
import com.example.andrii.gitwatcher.data.Database.RepositoryObjectDb
import com.example.andrii.gitwatcher.data.models.Repository
import com.example.andrii.gitwatcher.modules.reposytory.presenters.RepositoryPresenter
import com.example.andrii.gitwatcher.modules.startPage.view.StartPageActivity
import com.example.andrii.gitwatcher.tools.ImageTools
import kotlinx.android.synthetic.main.activity_repo.*


class RepositoryActivity:BaseActivity(){

    private lateinit var repository: Repository
    private lateinit var presenter: RepositoryPresenter
    private lateinit var fragmentFiles:FilesFragment

    companion object {
        private const val REPOSITORY_EXTRA = "REPOSITORY_EXTRA"

        fun newIntent(con: Context,repo:Repository): Intent {
            val intent = Intent(con, RepositoryActivity::class.java)
            intent.putExtra(REPOSITORY_EXTRA,repo)

            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        presenter = RepositoryPresenter()
        presenter.onAttach(this)

        repository = intent.getParcelableExtra(REPOSITORY_EXTRA)

        setToolbar()
        setupViewPager()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun onBackPressed() {
        if(repoPager.currentItem == 1 && fragmentFiles.getDirItemCount() > 1){
            fragmentFiles.onBackPressed()
        }
        else super.onBackPressed()
    }

    private fun setToolbar() {
        setSupportActionBar(repoToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        repoToolbarBack.setOnClickListener{ onBackPressed() }
        val tools = ImageTools(this)
        repoToolbarName.text = repository.name
        tools.downloadUserImage(repoToolbarPhoto,repository.owner!!,false)
        starsCount.text = repository.stargazers_count
        shareCount.text = repository.forks_count
        updatedWhen.text = formatData(repository.updated_at)
        sizeCount.text = formatSize(repository.size)
        repoToolbarLanguage.text = repository.language

        val color = getColorsMap()[repository.language]
        repoToolbarLanguage.setTextColor(ContextCompat.getColor(this,color ?: R.color.languageOther))

        presenter.setupFollowOnClick(repoToolbarFollowBtn,repository,realm)

        val realmObject = realm.where(RepositoryObjectDb::class.java)
                            .equalTo("repositoryId", repository.id)
                            .findFirst()
        if(realmObject != null ) {
            presenter.setupAnimation(repoToolbarFollowBtn)
        }else{
            presenter.setupAnimation(repoToolbarFollowBtn)
            presenter.setupAnimation(repoToolbarFollowBtn)
        }

    }

    private fun setupViewPager() {
        val fragmentReadme = ReadmeFragment.newInstance(repository.owner?.login?:"",repository.name ?:"")
        fragmentFiles = FilesFragment.newInstance(repository.owner?.login?:"",repository.name ?:"")

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(fragmentReadme,getString(R.string.readme))
        viewPagerAdapter.addFragment(fragmentFiles,getString(R.string.files))

        repoPager.adapter = viewPagerAdapter
        repoTabs.setupWithViewPager(repoPager)
    }


    override fun showProgress() {}
    override fun hideProgress() {}
    fun followingStateChanged() {
        StartPageActivity.dataChanged = true
    }

}
