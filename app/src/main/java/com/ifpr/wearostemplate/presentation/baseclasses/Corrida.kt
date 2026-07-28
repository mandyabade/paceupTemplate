package com.ifpr.wearostemplate.presentation

import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CorridasActivity {
    data class Corrida(
        val distanciaKm: Double = 0.0,
        val tempoSegundos: Long = 0,
        val ritmoMedio: String = "" ,
        val dataHora: String =""
    )

    private fun salvarCorrida(distanciaKm: Double, tempoSegundos:
    Long) {
        val database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("corridas")
        val id = referencia.push().key ?: return
        val data = SimpleDateFormat("dd/MM/yyyy HH:mm",
            Locale.getDefault()).format(Date())
        val ritmo = calcularRitmo(distanciaKm, tempoSegundos)
        val corrida = Corrida(distanciaKm, tempoSegundos, ritmo, data)
        referencia.child(id).setValue(corrida)
    }
    private fun calcularRitmo(distanciaKm: Double, tempoSegundos:
    Long): String {
        if (distanciaKm <= 0.0) return "0:00"
        val segundosPorKm = (tempoSegundos / distanciaKm).toInt()
        val minutos = segundosPorKm / 60
        val segundos = segundosPorKm % 60
        return "$minutos:"+ segundos.toString().padStart(2, '0')
    }

}