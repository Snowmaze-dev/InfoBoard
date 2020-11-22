package ru.snowmaze.domain.note

data class Note(val id: Long, val text: String) {

    constructor(text: String): this(0, text)

}