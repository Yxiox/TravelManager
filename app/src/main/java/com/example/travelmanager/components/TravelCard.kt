package com.example.travelmanager.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.travelmanager.R
import com.example.travelmanager.data.TravelPurposeEnum
import com.example.travelmanager.entity.Travel
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TravelCard(
    travel: Travel,
    onEdit: (Int) -> Unit,
    onCreateJournal: (Int) -> Unit,
    onDelete: (Travel) -> Unit
) {
    val dismissState = rememberDismissState()

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    LaunchedEffect(dismissState) {
        snapshotFlow { dismissState.currentValue }
            .collect { value ->
                if (value == DismissValue.DismissedToEnd) {
                    onDelete(travel)
                }
            }
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd),
        background = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .height(100.dp)
            ) {
                Row(
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                onEdit(travel.id)
                            }
                        )
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.padding(horizontal = 10.dp)) {
                        if (travel.finalidade == TravelPurposeEnum.lazer)
                            Image(
                                painter = painterResource(R.drawable.palmtree),
                                contentDescription = "Lazer",
                                modifier = Modifier.size(50.dp)
                            )
                        else
                            Icon(Icons.Default.Email, contentDescription = "", Modifier.size(50.dp))
                    }
                    Column(Modifier.padding(5.dp)) {
                        Text("Destino ${travel.destino}")
                        Text("Início: ${travel.inicio.format(dateFormatter)}")
                        Text("Fim: ${travel.fim!!.format(dateFormatter)}")
                        Text("Orçamento: ${currencyFormatter.format(travel.orcamento)}")
                    }
                    Column(modifier = Modifier.padding(start = 20.dp)) {
                        OutlinedButton(
                            onClick = { onCreateJournal(travel.id) },
                            modifier = Modifier.width(120.dp)
                        ) {
                            Text("Roteiro IA", textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    )
}
