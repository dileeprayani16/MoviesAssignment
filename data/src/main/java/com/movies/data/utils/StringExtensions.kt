package com.movies.data.utils

fun String?.orDefaultPlaceHolder(placeHolder: String = "-") = this ?: placeHolder