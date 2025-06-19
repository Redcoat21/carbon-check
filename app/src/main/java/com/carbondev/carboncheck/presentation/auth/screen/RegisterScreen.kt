package com.carbondev.carboncheck.presentation.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carbondev.carboncheck.R

@OptIn(ExperimentalMaterial3Api::class) // For TextFieldDefaults
@Composable
fun RegisterScreenCompose(
    onRegisterClicked: (String, String, String) -> Unit, // fullName, email, password
    onSignInClicked: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Colors (consider defining these in your Theme)
    val greenColor = Color(0xFF16A34A)
    val hintTextColor = Color(0xFF9CA3AF)
    val blackTextColor = Color.Black
    val whiteTextColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Added for scrollability if content overflows
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp)) // Initial spacing

        // Logo Section
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(greenColor)
                .align(Alignment.CenterHorizontally), // Explicitly center if needed in Column
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.eco), // Replace with your actual drawable
                contentDescription = "App Logo",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Inside
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Join CarbonCheck",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = blackTextColor
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Start tracking your carbon footprint",
            fontSize = 16.sp,
            color = blackTextColor
        )

        Spacer(modifier = Modifier.height(64.dp)) // Spacing before the form fields

        // Full Name Section
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.width(300.dp)) {
            Text(
                text = "Full Name",
                fontWeight = FontWeight.Bold,
                color = blackTextColor,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter your full name", color = hintTextColor) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = greenColor,
                    unfocusedIndicatorColor = hintTextColor,
                    focusedContainerColor = Color.Transparent, // Optional: for background
                    unfocusedContainerColor = Color.Transparent // Optional: for background
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Email Section
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.width(300.dp)) {
            Text(
                text = "Email",
                fontWeight = FontWeight.Bold,
                color = blackTextColor,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter your email", color = hintTextColor) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = greenColor,
                    unfocusedIndicatorColor = hintTextColor,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Password Section
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.width(300.dp)) {
            Text(
                text = "Password",
                fontWeight = FontWeight.Bold,
                color = blackTextColor,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter your password", color = hintTextColor) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = greenColor,
                    unfocusedIndicatorColor = hintTextColor,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onRegisterClicked(fullName, email, password) },
            modifier = Modifier
                .width(300.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = greenColor)
        ) {
            Text("Register", color = whiteTextColor, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp)) // Space before the "Already have an account?" text

        // Sign In Text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 16.dp) // Spacing from bottom of screen
        ) {
            Text(
                text = "Already have an account?",
                fontSize = 16.sp,
                color = blackTextColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = onSignInClicked) {
                Text(
                    text = "Sign in",
                    color = greenColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=360dp,height=740dp,dpi=420")
@Composable
fun RegisterScreenComposePreview() {
    MaterialTheme { // Replace with your actual app theme if available
        RegisterScreenCompose(onRegisterClicked = { _, _, _ -> }, onSignInClicked = {})
    }
}