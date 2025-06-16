package com.example.aplicaciongestion.navigation

sealed class Screens(val route: String) {
    object ListaMateriales : Screens("lista")
    object AgregarMaterial : Screens("agregar")
    // Puedes agregar más pantallas después
}
