package com.example.basemvvm3.classes.data

data class PersonItem(
    val id: Int,
    val name: String,
    val age: Int
)

data class PersonList(
    val name: String,
    val list: ArrayList<PersonItem>
)