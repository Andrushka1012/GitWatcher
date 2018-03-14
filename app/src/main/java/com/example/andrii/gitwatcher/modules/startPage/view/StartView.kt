package com.example.andrii.gitwatcher.modules.startPage.view

import com.example.andrii.gitwatcher.base.BaseView.MVPView

interface StartView: MVPView {
    fun setUpRecyclerView(fragment:RecyclerViewFragment,type:Int)
}