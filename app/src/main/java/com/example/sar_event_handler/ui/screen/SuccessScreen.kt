package com.example.sar_event_handler.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sar_event_handler.R
import com.example.sar_event_handler.model.Student

@Composable
fun SuccessScreen(
    student: Student,
    onNewRegistration: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.save_success),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                StudentInfoRow("Nama", student.name)
                StudentInfoRow("NIS", student.nis)
                StudentInfoRow("Kelas", student.className)
                StudentInfoRow("Tanggal Lahir", student.birthDate)
                StudentInfoRow("Jenis Kelamin", if (student.gender == "L") "Laki-laki" else "Perempuan")
                StudentInfoRow("Ekstrakurikuler", student.extracurricular.joinToString(", "))
                StudentInfoRow("Jadwal", student.schedule)
            }
        }
        
        Button(
            onClick = onNewRegistration,
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
        ) {
            Text("Daftar Baru")
        }
    }
}

@Composable
private fun StudentInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold
        )
        Text(text = value)
    }
}
