package com.example.chatapp.presentation.screen.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.ui.theme.ArrowBack
import com.example.chatapp.ui.theme.ArrowForward
import com.example.chatapp.ui.theme.PasswordIsVisible
import com.example.chatapp.ui.theme.PasswordNotVisible

@Composable
fun SignUpUsernameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(stringResource(R.string.username_sign_in))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.username_icon_sign_up)
            )
        },
        isError = errorMessage.isNotEmpty(),
        supportingText = {
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage)
            }
        },
        shape = RoundedCornerShape(24.dp),
        placeholder = {
            Text(stringResource(R.string.enter_your_username_sign_up))
        }
    )
}


@Composable
fun SignUpEmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        label = {
            Text(stringResource(R.string.email_sign_in))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = stringResource(R.string.email_icon_sign_in)
            )
        },
        isError = errorMessage?.isNotEmpty() ?: false,
        supportingText = {
            if (errorMessage?.isNotEmpty() ?: false) {
                Text(errorMessage)
            }
        },
        shape = RoundedCornerShape(24.dp),
        placeholder = {
            Text(stringResource(R.string.enter_your_email_sign_in))
        }
    )
}




@Composable
fun SignUpPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?,
    isPasswordVisible: Boolean,
    onIconClick: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        label = {
            Text(stringResource(R.string.password_sign_in))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Password,
                contentDescription = stringResource(R.string.password_icon_sign_in)
            )
        },
        isError = errorMessage?.isNotEmpty() ?: false,
        supportingText = {
            if (errorMessage?.isNotEmpty() ?: false) {
                Text(errorMessage)
            }
        },
        shape = RoundedCornerShape(24.dp),
        placeholder = {
            Text(stringResource(R.string.enter_your_password_sign_in))
        },
        visualTransformation = if(!isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            SignUpPasswordVisibilityIcon(
                isPasswordVisible = isPasswordVisible,
                onIconClick = onIconClick
            )
        }
    )
}


@Composable
fun SignUpPasswordVisibilityIcon(
    isPasswordVisible: Boolean,
    onIconClick: () -> Unit
) {
    if(isPasswordVisible) {
        Icon(
            modifier = Modifier.clickable(
                onClick = onIconClick
            ),
            imageVector = PasswordIsVisible,
            contentDescription = stringResource(R.string.password_visibility_off),
        )
    } else {
        Icon(
            modifier = Modifier.clickable(
                onClick = onIconClick
            ),
            imageVector = PasswordNotVisible,
            contentDescription = stringResource(R.string.password_visibility_on),
        )
    }
}

@Composable
fun SignUpButton(
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        enabled = enabled
    ) {
        Text(
            text = "Sign Up",
            fontSize = 20.sp
        )
    }
}

@Composable
fun ForwardIcon(
    onClick: () -> Unit
) {
    Icon(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        imageVector = ArrowForward,
        contentDescription = stringResource(R.string.back_icon)
    )
}


