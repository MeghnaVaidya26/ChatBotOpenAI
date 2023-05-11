package com.app.view.listeners

interface OnListClickListener {
    fun onListClick(position: Int, obj: Any?)
    fun onListClickSimple(position: Int, obj: Any?)
    fun onListClickView(position: Int, obj: Any?)
    fun onReardedSucessView(obj: Any?)

}