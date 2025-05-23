package com.carbondev.carboncheck.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carbondev.carboncheck.R

@Composable
fun LoginScreenCompose(
    onSignInClicked: (String, String) -> Unit,
    onSignUpClicked: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Colors from your XML (you might want to define these in your Theme)
    val greenColor = Color(0xFF16A34A)
    val hintTextColor = Color(0xFF9CA3AF)
    val blackTextColor = Color.Black // Or Color(0xFF000000)
    val whiteTextColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // A general padding for the screen
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Start from top, elements will push down
    ) {
        Spacer(modifier = Modifier.height(32.dp)) // Initial spacing from top

        // Logo Section
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape) // Makes the FrameLayout circular
                .background(greenColor), // Assuming circle_green is a green circle drawable
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
            text = "Welcome Back",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = blackTextColor
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Sign in to CarbonCheck",
            fontSize = 16.sp,
            color = blackTextColor // Or a lighter gray if preferred
        )

        Spacer(modifier = Modifier.weight(1f, fill = false).padding(bottom = 64.dp)) // Pushes content below

        // Email Section
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.width(300.dp)) {
            Text(
                text = "Email",
                fontWeight = FontWeight.Bold,
                color = blackTextColor
            )
            Spacer(modifier = Modifier.height(4.dp)) // Space between label and TextField
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter your email", color = hintTextColor) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = greenColor, // For the border when focused
                    unfocusedIndicatorColor = hintTextColor, // For the border when unfocused
                    // You might also want to customize other colors:
                    // focusedTextColor = blackTextColor,
                    // unfocusedTextColor = blackTextColor,
                    // cursorColor = greenColor,
                    // focusedLabelColor = greenColor,
                    // unfocusedLabelColor = hintTextColor,
                    // focusedPlaceholderColor = hintTextColor, // If you're using placeholder instead of label
                    // unfocusedPlaceholderColor = hintTextColor, // If you're using placeholder instead of label
                    // containerColor = Color.Transparent // Or your desired background color for the field
                )
            )
        }


        Spacer(modifier = Modifier.height(24.dp))

        // Password Section
        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.width(300.dp)) {
            Text(
                text = "Password",
                fontWeight = FontWeight.Bold,
                color = blackTextColor
            )
            Spacer(modifier = Modifier.height(4.dp)) // Space between label and TextField
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
                    unfocusedIndicatorColor = hintTextColor
                )

            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onSignInClicked(email, password) },
            modifier = Modifier
                .width(300.dp)
                .height(48.dp), // A standard button height
            colors = ButtonDefaults.buttonColors(containerColor = greenColor)
        ) {
            Text("Sign In", color = whiteTextColor, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f, fill = false).padding(bottom = 24.dp)) // Pushes the sign up text to bottom

        // Sign Up Text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 16.dp) // Spacing from bottom of screen
        ) {
            Text(
                text = "Don't have an account?",
                fontSize = 16.sp,
                color = blackTextColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(onClick = onSignUpClicked) {
                Text(
                    text = "Sign up",
                    color = greenColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Helper to define text styles if you want to reuse them
object AppTextStyles {
    val inputHintStyle = androidx.compose.ui.text.TextStyle(
        color = Color(0xFF9CA3AF),
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif
    )
    // Add other styles
}


@Preview(showBackground = true)
@Composable
fun LoginScreenComposePreview() {
    // You'll need to define a theme or wrap with MaterialTheme for previews to work well
    MaterialTheme {
        LoginScreenCompose(onSignInClicked = { _, _ -> }, onSignUpClicked = {})
    }
}