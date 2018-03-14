package com.example.andrii.gitwatcher.data.models

class SearchUsers{
    var total_count:Int? = null
    var items :ArrayList<User>? = null

    constructor(total_count: Int?, items: ArrayList<User>?) {
        this.total_count = total_count
        this.items = items
    }
}