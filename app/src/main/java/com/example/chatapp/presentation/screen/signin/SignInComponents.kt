package com.example.chatapp.presentation.screen.signin

import android.R.attr.contentDescription
import android.R.attr.onClick
import android.R.attr.text
import android.widget.Button
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.ui.theme.ArrowBack
import com.example.chatapp.ui.theme.PasswordIsVisible
import com.example.chatapp.ui.theme.PasswordNotVisible


@Composable
fun SignInEmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String
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
        isError = errorMessage.isNotEmpty(),
        supportingText = {
            if (errorMessage.isNotEmpty()) {
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
fun SignInPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String,
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
        isError = errorMessage.isNotEmpty(),
        supportingText = {
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage)
            }
        },
        shape = RoundedCornerShape(24.dp),
        placeholder = {
            Text(stringResource(R.string.enter_your_password_sign_in))
        },
        visualTransformation = if(!isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            PasswordVisibilityIcon(
                isPasswordVisible = isPasswordVisible,
                onIconClick = onIconClick
            )
        }
    )
}


@Composable
fun PasswordVisibilityIcon(
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
fun SignInButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = "Sign In",
            fontSize = 20.sp
        )
    }
}


@Composable
fun CustomClickableText(
    onClick: () -> Unit,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        textAlign = TextAlign.Center,
        text = stringResource(R.string.don_t_have_an_account_sign_up),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.bodyMedium,
        textDecoration = TextDecoration.Underline
    )
}


@Composable
fun BackIcon(
    onClick: () -> Unit
) {
    Icon(
        modifier = Modifier
            .size(48.dp)
            .clickable(onClick = onClick),
        imageVector = ArrowBack,
        contentDescription = stringResource(R.string.back_icon)
    )
}

