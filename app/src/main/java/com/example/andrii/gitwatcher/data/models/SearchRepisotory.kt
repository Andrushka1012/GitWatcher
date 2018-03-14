package com.example.andrii.gitwatcher.data.models

class SearchRepisotory{
    var total_count:Int? = null
    var items :ArrayList<Repository>? = null

    constructor(total_count: Int?, items: ArrayList<Repository>?) {
        this.total_count = total_count
        this.items = items
    }
}


