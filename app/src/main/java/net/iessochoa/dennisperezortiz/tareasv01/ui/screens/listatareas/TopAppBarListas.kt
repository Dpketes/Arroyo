import android.content.Intent
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import net.iessochoa.dennisperezortiz.tareasv01.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarListas() {
    val context = LocalContext.current

    //Estado para manejar el menú desplegable
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(text = "Lista de Tareas")
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            //Accion de bola del mundo para abrir el enlace del instituto.
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://portal.edu.gva.es/03013224/"))
                context.startActivity(intent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_public),
                    contentDescription = "Abrir portal del instituto"
                )
            }
            //Accion Morevert para abrir un dropdown con dos acciones más: llamar al instituto y configuración.
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Menú de opciones"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Llamar") },
                    onClick = {
                        expanded = false
                        // Realizar llamada al instituto
                        val phoneNumber = "tel:123456789" // Cambia este número por el número real
                        val intent = Intent(Intent.ACTION_CALL).apply {
                            data = android.net.Uri.parse(phoneNumber)
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: SecurityException) {
                            Toast.makeText(context, "Permiso de llamada no concedido", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                DropdownMenuItem(
                    text = { Text("Configuración") },
                    onClick = {
                        expanded = false
                        Toast.makeText(context, "Configuración...", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    )
}