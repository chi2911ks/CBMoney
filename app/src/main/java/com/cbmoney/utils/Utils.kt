package com.cbmoney.utils

fun main(){
    rac()
}

fun rac(){
    val ll = listOf(101,102,103,101,104,102)
    val l1 = mutableListOf<Int>()
    ll.forEach {
        if(!l1.contains(it)) l1.add(it)
        else l1.remove(it)
    }
    println(l1)
}
