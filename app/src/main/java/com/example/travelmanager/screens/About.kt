package com.example.travelmanager.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun About (){
    Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(30.dp)) {
        Text("Sobre o APP", style = MaterialTheme.typography.h2, modifier = Modifier.padding(bottom = 10.dp), color =  Color.White)
        Text("Planeje sua viagem de forma inteligente com nosso app! ✈\uFE0F\uD83D\uDDFA\uFE0F\n\n" +
                "Organizar uma viagem nunca foi tão fácil! Com nosso aplicativo, você pode registrar e acompanhar todos os detalhes da sua jornada em um só lugar. Planeje sua data de saída e retorno, defina seu destino e finalidade, controle seu orçamento e, para tornar tudo ainda mais prático, gere roteiros personalizados com inteligência artificial.\n" +
                "Nossa IA analisa seu orçamento e preferências para criar um roteiro sob medida, garantindo que você aproveite cada momento sem preocupações.\n" +
                "Viaje com mais organização, segurança e praticidade. Baixe agora e descubra uma nova forma de explorar o mundo! \uD83D\uDE80\n", style = MaterialTheme.typography.body1, color =  Color.White)
    }
}