package com.example.tantargylista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tantargylista.ui.theme.TantargyListaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TantargyListaTheme {

                var isLoggedIn by remember { mutableStateOf(false) }

                if (isLoggedIn) {
                    SubjectListScreen(
                        onLogout = {
                            isLoggedIn = false
                        }
                    )
                } else {
                    ModernLoginScreen {
                        isLoggedIn = true
                    }
                }
            }
        }
    }
}

@Composable
fun ModernLoginScreen(onLoginSuccess: () -> Unit) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    val demoUsername = "admin"
    val demoPassword = "1234"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier.padding(24.dp),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "🎓 Tantárgylista",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Bejelentkezés",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Felhasználónév") }
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Jelszó") },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (username == demoUsername && password == demoPassword) {
                            error = false
                            onLoginSuccess()
                        } else {
                            error = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Belépés")
                }

                if (error) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hibás felhasználónév vagy jelszó!",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
