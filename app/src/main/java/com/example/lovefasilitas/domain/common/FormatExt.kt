package com.example.lovefasilitas.domain.common

import java.text.NumberFormat
import java.util.Locale

fun Int.toRupiah(): String {
    val formatter = NumberFormat.getIntegerInstance(Locale("id", "ID"))
    return "Rp ${formatter.format(this)}"
}

fun Int.toRupiahPerTrip(): String {
    val formatter = NumberFormat.getIntegerInstance(Locale("id", "ID"))
    return "Rp ${formatter.format(this)} / hari"
}
