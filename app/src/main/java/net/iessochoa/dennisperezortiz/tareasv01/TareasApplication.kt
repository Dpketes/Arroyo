package net.iessochoa.dennisperezortiz.tareasv01

import android.app.Application
import net.iessochoa.dennisperezortiz.tareasv01.data.repository.Repository

class TareasApplication: Application() {

    companion object{
        lateinit var application: TareasApplication
    }
    override fun onCreate() {
        super.onCreate()
        application = this
        //iniciamos el Repository
        Repository()
    }
}