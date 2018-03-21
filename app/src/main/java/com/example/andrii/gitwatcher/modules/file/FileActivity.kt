package com.example.andrii.gitwatcher.modules.file

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.base.BaseView.BaseActivity
import com.example.andrii.gitwatcher.data.models.FileModel
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import kotlinx.android.synthetic.main.activity_file.*

class FileActivity:BaseActivity(){

    companion object {
        private const val FILE_MODEL_EXTRA = "FILE_MODEL_EXTRA"
        fun newIntent(context:Context,model:FileModel): Intent {
            val intent = Intent(context,FileActivity::class.java)
            intent.putExtra(FILE_MODEL_EXTRA,model)

            return intent
        }
    }

    private lateinit var model:FileModel
    private lateinit var presenter:FileActivityPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)
        presenter = FileActivityPresenter()
        presenter.onAttach(this)

        model = intent.getParcelableExtra(FILE_MODEL_EXTRA)
        setupToolbar()
        presenter.downloadContent(model.download_url!!)
    }

    private fun setupToolbar() {
        setSupportActionBar(fileToolbar)
        supportActionBar!!.title = model.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun showFile(markdown: String?) {
        val text: String? = markdown ?: return

            codeView.setOptions(Options.Default.get(this)
                    .withCode(text!!)
                    .withTheme(ColorTheme.MONOKAI))



    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

}