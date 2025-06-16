package org.example.aplicaciongestion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.*
import com.example.aplicaciongestion.database.DatabaseFactory
import com.example.aplicaciongestion.navigation.Screens
import com.example.aplicaciongestion.ui.MaterialesScreen
import com.example.aplicaciongestion.ui.AgregarMaterialScreen
import com.example.aplicaciongestion.viewmodel.MaterialViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = DatabaseFactory.create(applicationContext)

        setContent {
            val navController = rememberNavController()
            val materialViewModel: MaterialViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MaterialViewModel(db) as T
                    }
                }
            )

            NavHost(navController = navController, startDestination = Screens.ListaMateriales.route) {
                composable(Screens.ListaMateriales.route) {
                    MaterialesScreen(
                        navController = navController,
                        viewModel = materialViewModel
                    )
                }
                composable(Screens.AgregarMaterial.route) {
                    AgregarMaterialScreen(
                        viewModel = materialViewModel,
                        onMaterialAgregado = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
