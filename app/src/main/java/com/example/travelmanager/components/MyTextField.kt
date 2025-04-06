package com.example.myregistry.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyTextField(value:String, onValueChange: (String) -> Unit, label:String, required:Boolean){
    var isTouched = remember {
        mutableStateOf(false)
    }
    var focus = remember {
        FocusRequester()
    }
    OutlinedTextField(value = value,
        onValueChange = {
            isTouched.value = true
            onValueChange(it)
        },
        Modifier.padding(vertical = 5.dp).focusRequester(focus).onFocusEvent { if (it.hasFocus) {
        isTouched.value = true
        }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
            cursorColor = Color.White,
            unfocusedBorderColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            disabledTextColor = Color.Gray,
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White),


        label = { Text(text = label) },
        supportingText = {if (required) {if (isTouched.value && value.isBlank()){
            Text(text = "Field $label is required")
        } }},
        isError = if (required) {value.isBlank() && isTouched.value} else false
    )
}