package com.example.sar_event_handler.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sar_event_handler.data.DataRepository
import com.example.sar_event_handler.model.Student
import com.example.sar_event_handler.ui.screen.RegistrationScreen
import com.example.sar_event_handler.ui.screen.SuccessScreen

sealed class Screen(val route: String) {
    object Registration : Screen("registration")
    object Success : Screen("success")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentStudent = remember { androidx.compose.runtime.mutableStateOf<Student?>(null) }
    
    NavHost(
        navController = navController,
        startDestination = Screen.Registration.route
    ) {
        composable(Screen.Registration.route) {
            RegistrationScreen(
                onRegistrationComplete = { student ->
                    // Save the student data to the repository
                    DataRepository.registerStudent(student)
                    
                    // Store the current student for the success screen
                    currentStudent.value = student
                    
                    // Navigate to the success screen
                    navController.navigate(Screen.Success.route)
                }
            )
        }
        
        composable(Screen.Success.route) {
            // Display the success screen with the registered student data
            currentStudent.value?.let { student ->
                SuccessScreen(
                    student = student,
                    onNewRegistration = {
                        // Navigate back to registration screen for new entry
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
