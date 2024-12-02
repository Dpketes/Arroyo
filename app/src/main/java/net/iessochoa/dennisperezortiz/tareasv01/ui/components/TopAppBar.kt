package net.iessochoa.dennisperezortiz.tareasv01.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    tituloPantallaActual: String,
    puedeNavegarAtras: Boolean,
    navegaAtras: () -> Unit = {},
    modifier: Modifier = Modifier
){
     TopAppBar(
        title = {
            Text(text = tituloPantallaActual)
        },
        navigationIcon = {
            if (puedeNavegarAtras) {
                IconButton(onClick = navegaAtras) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            }
        },
        modifier = modifier
    )
}