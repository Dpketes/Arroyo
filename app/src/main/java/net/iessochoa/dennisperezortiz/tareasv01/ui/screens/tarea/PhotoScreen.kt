package net.iessochoa.dennisperezortiz.tareasv01.ui.screens.tarea

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import net.iessochoa.dennisperezortiz.tareasv01.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagenScreen(
    uriImagen: String,
    onVolver: () -> Unit,
    navController: NavController
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Foto") },
                navigationIcon = {
                    IconButton(onClick = { onVolver() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary) // Definimos el color de fondo
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = if (uriImagen.isEmpty()) R.drawable.sinimagen else uriImagen,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    )
}
