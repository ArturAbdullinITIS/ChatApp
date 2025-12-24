package com.example.chatapp.presentation.screen.signin

import android.R.attr.text
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.chatapp.R
import com.example.chatapp.presentation.navigation.Screen
import com.example.chatapp.ui.theme.PoppinsFontFamily


@Composable
fun SignInScreen() {
    SignInContent()
}

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            BackIcon(
                onClick = {}
            )
            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = stringResource(R.string.hello_welcome_back_sign_in),
                fontSize = 24.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.login_to_your_account_sign_in),
                fontSize = 16.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(32.dp))
            SignInEmailTextField(
                value = state.email,
                onValueChange = {
                    viewModel.processCommand(SignInCommand.InputEmail(it))
                },
                errorMessage = state.emailError
            )
            Spacer(modifier = Modifier.height(16.dp))
            SignInPasswordTextField(
                value = state.password,
                onValueChange = {
                    viewModel.processCommand(SignInCommand.InputPassword(it))
                },
                errorMessage = state.passwordError,
                isPasswordVisible = state.isPasswordVisible,
                onIconClick = {
                    viewModel.processCommand(SignInCommand.ChangePasswordVisibility)
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            SignInButton(
                onClick = {
                    viewModel.processCommand(SignInCommand.Login)
                },
                enabled = !state.isLoading
            )
            if (state.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            CustomClickableText(
                onClick = {}
            )
        }
    }
}