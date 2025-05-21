package com.example.sar_event_handler.data

import com.example.sar_event_handler.model.Extracurricular
import com.example.sar_event_handler.model.Student

object DataRepository {
    // List of available extracurricular activities
    val extracurricularOptions = listOf(
        Extracurricular("Basketball"),
        Extracurricular("Futsal"),
        Extracurricular("Volleyball"),
        Extracurricular("Badminton"),
        Extracurricular("Music"),
        Extracurricular("Dance")
    )
    
    // List of available classes
    val classes = listOf("X IPA 1", "X IPA 2", "X IPS 1", "X IPS 2", 
                        "XI IPA 1", "XI IPA 2", "XI IPS 1", "XI IPS 2",
                        "XII IPA 1", "XII IPA 2", "XII IPS 1", "XII IPS 2")
                        
    // Store registered students
    private val _registeredStudents = mutableListOf<Student>()
    val registeredStudents: List<Student> = _registeredStudents
    
    fun registerStudent(student: Student) {
        _registeredStudents.add(student)
    }
}
