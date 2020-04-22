package com.example.basemvvm3.helper

import com.example.basemvvm3.classes.data.PersonItem

interface EventAction {
    fun openEventDetail(person: PersonItem)
}