package com.example.sar_event_handler.model

data class Student(
    val name: String = "",
    val nis: String = "",
    val className: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val extracurricular: List<String> = emptyList(),
    val schedule: String = ""
)
