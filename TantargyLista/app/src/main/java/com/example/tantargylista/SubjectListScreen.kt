package com.example.tantargylista

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tantargylista.data.Subject

@Composable
fun SubjectListScreen(
    onLogout: () -> Unit,
    viewModel: SubjectViewModel = viewModel()
) {
    val subjects by viewModel.subjects.collectAsState()

    var name by remember { mutableStateOf("") }
    var credit by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf<Subject?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {

        Column {

            // SZÍNES FEJLÉC
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                        )
                    ),
                contentAlignment = Alignment.BottomStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "📘 Tantárgylista",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    TextButton(onClick = onLogout) {
                        Text(
                            text = "Kijelentkezés",
                            color = Color.White
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(12.dp))

            // BEVITELI KÁRTYA
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Tantárgy neve") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = credit,
                        onValueChange = { credit = it },
                        label = { Text("Kredit") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (name.isNotBlank() && credit.isNotBlank()) {
                                if (selectedSubject == null) {
                                    viewModel.addSubject(name, credit.toInt())
                                } else {
                                    viewModel.updateSubject(
                                        selectedSubject!!.copy(
                                            name = name,
                                            credit = credit.toInt()
                                        )
                                    )
                                    selectedSubject = null
                                }

                                name = ""
                                credit = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (selectedSubject == null)
                                "Hozzáadás"
                            else
                                "Módosítás"
                        )
                    }
                }
            }

            // LISTA
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {
                items(subjects) { subject ->
                    ModernSubjectCard(
                        subject = subject,
                        onEdit = {
                            name = subject.name
                            credit = subject.credit.toString()
                            selectedSubject = subject
                        },
                        onDelete = {
                            viewModel.deleteSubject(subject)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ModernSubjectCard(
    subject: Subject,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = subject.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = "Kredit: ${subject.credit}",
                    color = Color(0xFF64B5F6)
                )
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color(0xFFFFC107)
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color(0xFFFF5252)
                    )
                }
            }
        }
    }
}
