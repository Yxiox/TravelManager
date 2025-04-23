package com.example.travelmanager.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.travelmanager.entity.Travel

@Composable
fun TravelCard(travel: Travel,
               onEdit:(Int) -> Unit){
    Card (modifier = Modifier.fillMaxWidth().padding(15.dp).height(80.dp)){
        Row (modifier = Modifier.pointerInput(Unit){
            detectTapGestures (
                onLongPress = {
                    onEdit(travel.id)
                }
            )
        }) {
            Column {
                if (travel.finalidade == "lazer") Icon(Icons.Default.Favorite, contentDescription = "") else Icon(Icons.Default.Email, contentDescription = "")
            }
            Column (Modifier.padding(5.dp )){
                Text("Finalidade ${travel.finalidade}")
                Text("Destino ${travel.destino}")
                Text("Inicio ${travel.inicio}")
                Text("Fim ${travel.fim}")

            }

        }
    }
}
