package com.example.myregistry.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import java.text.NumberFormat
import java.util.Locale

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
        Modifier.padding(vertical = 5.dp).fillMaxWidth().focusRequester(focus).onFocusEvent { if (it.hasFocus) {
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
            Text(text = "O campo $label é obrigatório")
        } }},
        isError = if (required) {value.isBlank() && isTouched.value} else false
    )
}



@Composable
fun DecimalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    required: Boolean = false
) {
    var isTouched by remember { mutableStateOf(false) }
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))


    OutlinedTextField(
        value = value,
        onValueChange = {
            isTouched = true
            val sanitized = it
                .replace(",", ".") // aceita vírgula como ponto
                .filter { c -> c.isDigit() || c == '.' }
                .let { input ->
                    // Limita a um ponto decimal
                    val parts = input.split(".")
                    if (parts.size <= 2) {
                        parts[0] + if (parts.size == 2) "." + parts[1].take(2) else ""
                    } else {
                        parts[0] + "." + parts[1].take(2)
                    }
                }

            onValueChange(sanitized)
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        isError = required && isTouched && value.isBlank(),
        supportingText = {
            if (required && isTouched && value.isBlank()) {
                Text("O campo \"$label\" é obrigatório")
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
            cursorColor = Color.White,
            unfocusedBorderColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}
