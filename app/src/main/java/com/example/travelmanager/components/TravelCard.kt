package com.example.travelmanager.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.travelmanager.R
import com.example.travelmanager.entity.Travel

@Composable
fun TravelCard(travel: Travel,
               onEdit:(Int) -> Unit){
    Card (modifier = Modifier.fillMaxWidth().padding(15.dp).height(100.dp)){
        Row (modifier = Modifier.pointerInput(Unit){
            detectTapGestures (
                onLongPress = {
                    onEdit(travel.id)
                }
            )
        },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (Modifier.padding(horizontal = 10.dp)) {
                if (travel.finalidade == "lazer") Image(painter = painterResource(R.drawable.palmtree), contentDescription = "Lazer", modifier = Modifier.size(50.dp)) else Icon(Icons.Default.Email, contentDescription = "", Modifier.size(50.dp))
            }
            Column (Modifier.padding(5.dp )){
                Text("Destino ${travel.destino}")
                Text("Início: ${travel.inicio}")
                Text("Fim: ${travel.fim}")
                Text("Orçamento: R$ ${travel.orcamento}")

            }

        }
    }
}
