package com.example.chatapp.presentation.screen.signup

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.chatapp.R
import com.example.chatapp.presentation.navigation.Screen
import com.example.chatapp.presentation.screen.signin.BackIcon
import com.example.chatapp.presentation.screen.signup.SignUpButton
import com.example.chatapp.presentation.screen.signup.SignUpCommand
import com.example.chatapp.presentation.screen.signup.SignUpEmailTextField
import com.example.chatapp.presentation.screen.signup.SignUpPasswordTextField
import com.example.chatapp.presentation.screen.signup.SignUpUsernameTextField
import com.example.chatapp.presentation.screen.signup.SignUpViewModel
import com.example.chatapp.ui.theme.PoppinsFontFamily


@Composable
fun SignUpScreen(
    onNavigateToChatList: () -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    SignUpContent(onNavigateToChatList, onNavigateToSignIn)
}

@Composable
fun SignUpContent(
    onNavigateToChatList: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if(state.isSuccess) {
            onNavigateToChatList()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                ForwardIcon(
                    onClick = {
                        onNavigateToSignIn()
                    }
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.create_your_account),
                fontSize = 24.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(

                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.or_sign_in_if_you_already_have_an_account),
                fontSize = 16.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(32.dp))
            SignUpUsernameTextField(
                value = state.username,
                onValueChange = {
                    viewModel.processCommand(SignUpCommand.InputUsername(it))
                },
                errorMessage = state.usernameError
            )
            Spacer(modifier = Modifier.height(8.dp))
            SignUpEmailTextField(
                value = state.email,
                onValueChange = {
                    viewModel.processCommand(SignUpCommand.InputEmail(it))
                },
                errorMessage = state.emailError
            )
            Spacer(modifier = Modifier.height(8.dp))
            SignUpPasswordTextField(
                value = state.password,
                onValueChange = {
                    viewModel.processCommand(SignUpCommand.InputPassword(it))
                },
                errorMessage = state.passwordError,
                isPasswordVisible = state.isPasswordVisible,
                onIconClick = {
                    viewModel.processCommand(SignUpCommand.ChangePasswordVisibility)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
            Spacer(modifier = Modifier.height(24.dp))
            SignUpButton(
                onClick = {
                    viewModel.processCommand(SignUpCommand.Register)
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
        }
    }
}