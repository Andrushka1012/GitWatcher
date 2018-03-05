package com.example.andrii.gitwatcher.base.BasePresenter

import com.example.andrii.gitwatcher.base.BaseView.MVPView

interface MVPPresenter<V : MVPView> {

    fun onAttach(view: V?)

    fun onDetach()

    fun getView(): V?

}
