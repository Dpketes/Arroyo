package net.iessochoa.dennisperezortiz.tareasv01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.iessochoa.dennisperezortiz.tareasv01.ui.navigation.AppNavigation
import net.iessochoa.dennisperezortiz.tareasv01.ui.theme.TareasV01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TareasV01Theme {
                AppNavigation()
            }
        }
    }
}