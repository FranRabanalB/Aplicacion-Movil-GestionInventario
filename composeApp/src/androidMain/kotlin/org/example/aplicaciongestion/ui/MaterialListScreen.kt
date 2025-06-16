package com.example.aplicaciongestion.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aplicaciongestion.database.Material
import com.example.aplicaciongestion.viewmodel.MaterialViewModel
import com.example.aplicaciongestion.navigation.Screens

@Composable
fun MaterialesScreen(
    navController: NavController,
    viewModel: MaterialViewModel
) {
    val listaMateriales by viewModel.materiales.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var materialSeleccionado by remember { mutableStateOf<Material?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Materiales ingresados", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listaMateriales) { material ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = material.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = "${material.cantidad} uds - $${material.valor}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Row {
                                IconButton(onClick = {
                                    materialSeleccionado = material
                                    showEditDialog = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                                }

                                IconButton(onClick = {
                                    materialSeleccionado = material
                                    showDeleteDialog = true
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }
                            }
                        }
                    }
                }
            }

            if (showDeleteDialog && materialSeleccionado != null) {
                AlertDialog(
                    onDismissRequest = {
                        showDeleteDialog = false
                        materialSeleccionado = null
                    },
                    title = { Text("Eliminar material") },
                    text = { Text("¿Seguro que quieres eliminar \"${materialSeleccionado!!.nombre}\"?") },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.eliminarMaterial(materialSeleccionado!!)
                            showDeleteDialog = false
                            materialSeleccionado = null
                        }) { Text("Eliminar") }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDeleteDialog = false
                            materialSeleccionado = null
                        }) { Text("Cancelar") }
                    }
                )
            }

            if (showEditDialog && materialSeleccionado != null) {
                var nombre by remember { mutableStateOf(materialSeleccionado!!.nombre) }
                var descripcion by remember { mutableStateOf(materialSeleccionado!!.descripcion) }
                var cantidad by remember { mutableStateOf(materialSeleccionado!!.cantidad.toString()) }
                var valor by remember { mutableStateOf(materialSeleccionado!!.valor.toString()) }

                AlertDialog(
                    onDismissRequest = {
                        showEditDialog = false
                        materialSeleccionado = null
                    },
                    title = { Text("Editar material") },
                    text = {
                        Column {
                            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
                            OutlinedTextField(value = cantidad, onValueChange = { cantidad = it }, label = { Text("Cantidad") })
                            OutlinedTextField(value = valor, onValueChange = { valor = it }, label = { Text("Valor") })
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.editarMaterial(
                                original = materialSeleccionado!!,
                                actualizado = Material(
                                    id = materialSeleccionado!!.id,
                                    nombre = nombre,
                                    descripcion = descripcion,
                                    cantidad = cantidad.toLongOrNull() ?: 0L,
                                    valor = valor.toDoubleOrNull() ?: 0.0
                                )
                            )
                            showEditDialog = false
                            materialSeleccionado = null
                        }) { Text("Guardar") }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showEditDialog = false
                            materialSeleccionado = null
                        }) { Text("Cancelar") }
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate(Screens.AgregarMaterial.route) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar material")
        }
    }
}
