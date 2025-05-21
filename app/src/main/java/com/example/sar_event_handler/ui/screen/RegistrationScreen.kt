package com.example.sar_event_handler.ui.screen

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sar_event_handler.R
import com.example.sar_event_handler.data.DataRepository
import com.example.sar_event_handler.model.Extracurricular
import com.example.sar_event_handler.model.Student
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    onRegistrationComplete: (Student) -> Unit
) {
    val context = LocalContext.current
    
    // Form states
    var name by remember { mutableStateOf("") }
    var nis by remember { mutableStateOf("") }
    var selectedClass by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }
    
    val extracurriculars = remember { 
        DataRepository.extracurricularOptions.map { 
            mutableStateOf(it.copy(isSelected = false)) 
        }
    }
    
    val calendar = Calendar.getInstance()
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            calendar.set(year, month, day)
            birthDate = dateFormatter.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_registration),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        // Name field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.label_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        
        // NIS field
        OutlinedTextField(
            value = nis,
            onValueChange = { nis = it },
            label = { Text(stringResource(R.string.label_nis)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
          // Class field (dropdown)
        var isClassMenuExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = isClassMenuExpanded,
            onExpandedChange = { isClassMenuExpanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = selectedClass,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.label_class)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isClassMenuExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = isClassMenuExpanded,
                onDismissRequest = { isClassMenuExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                DataRepository.classes.forEach { classOption ->
                    DropdownMenuItem(
                        text = { Text(classOption) },
                        onClick = { 
                            selectedClass = classOption
                            isClassMenuExpanded = false 
                        }
                    )
                }
            }
        }        // Birth date field (date picker)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = birthDate,
                onValueChange = { /* No change as this is controlled by the date picker */ },
                label = { Text(stringResource(R.string.label_birth_date)) },
                readOnly = true,
                enabled = false, // Disabling the field prevents it from capturing clicks
                trailingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            
            // Invisible overlay to capture clicks
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null, // No visual indication
                        onClick = { datePickerDialog.show() }
                    )
            )
        }
        
        // Gender field (radio buttons)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .selectableGroup()
        ) {
            Text(
                text = stringResource(R.string.label_gender),
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = selectedGender == "L",
                            onClick = { selectedGender = "L" },
                            role = Role.RadioButton
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedGender == "L",
                        onClick = null // null because the parent is already selectable
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.gender_male))
                }
                
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = selectedGender == "P",
                            onClick = { selectedGender = "P" },
                            role = Role.RadioButton
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedGender == "P",
                        onClick = null // null because the parent is already selectable
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.gender_female))
                }
            }
        }
        
        // Extracurricular field (checkboxes)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.label_extracurricular),
                modifier = Modifier.padding(vertical = 4.dp)
            )
            extracurriculars.chunked(2).forEach { rowItems ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { extracurricular ->
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = extracurricular.value.isSelected,
                                onCheckedChange = { isChecked ->
                                    extracurricular.value = extracurricular.value.copy(isSelected = isChecked)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = extracurricular.value.name)
                        }
                    }
                    // If odd number of items in row, add a blank space for the missing item
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        
        // Schedule field
        OutlinedTextField(
            value = schedule,
            onValueChange = { schedule = it },
            label = { Text(stringResource(R.string.label_schedule)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        
        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* Cancel action */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = stringResource(R.string.btn_cancel))
            }
            
            Button(
                onClick = { 
                    val selectedExtracurriculars = extracurriculars
                        .filter { it.value.isSelected }
                        .map { it.value.name }
                    
                    val student = Student(
                        name = name,
                        nis = nis,
                        className = selectedClass,
                        birthDate = birthDate,
                        gender = selectedGender,
                        extracurricular = selectedExtracurriculars,
                        schedule = schedule
                    )
                    
                    onRegistrationComplete(student)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = stringResource(R.string.btn_save))
            }
        }
    }
}
