package net.iessochoa.dennisperezortiz.tareasv01.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import net.iessochoa.dennisperezortiz.tareasv01.R
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

/**
 * Crea un nombre de fichero con la fecha y hora actual
 */
fun nombreArchivo(): String {
    val timestamp = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
        .format(System.currentTimeMillis())
    return "$timestamp.png"
}

/**
 * guarda una imagen en la galería de la aplicación y devuelve su uri
 */
suspend fun saveBitmapImage(context: Context, bitmap: Bitmap): Uri? {
    val TAG = context.getString(R.string.app_name)
    val timestamp = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
    var uri: Uri? = null

    val values = ContentValues().apply {
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + context.getString(R.string.app_name))
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
    }

    try {
        uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri == null) {
            Log.e(TAG, "No se pudo insertar en el ContentResolver.")
            return null
        }

        context.contentResolver.openOutputStream(uri).use { outputStream ->
            if (outputStream == null) {
                Log.e(TAG, "No se pudo abrir el OutputStream.")
                return null
            }

            if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
                Log.e(TAG, "Error al comprimir el bitmap.")
                return null
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            context.contentResolver.update(uri, values, null, null)
        }
    } catch (e: Exception) {
        Log.e(TAG, "Error al guardar la imagen: ${e.message}", e)
        uri?.let { context.contentResolver.delete(it, null, null) }
        return null
    }

    Log.d(TAG, "Imagen guardada con éxito: $uri")
    return uri
}


/**
 * Carga una imagen desde una uri.
 * @return null si no se ha podido cargar la imagen. Devuelve la imagen
 */
fun loadFromUri(context: Context, photoUri: Uri?): Bitmap? {
    var image: Bitmap? = null
    try {
        // check version of Android on device
        image = if (Build.VERSION.SDK_INT > 27) {
            // on newer versions of Android, use the new decodeBitmap method
            val source = ImageDecoder.createSource(
                context.contentResolver,
                photoUri!!
            )
            ImageDecoder.decodeBitmap(source)
        } else {
            // support older versions of Android by using getBitmap
            MediaStore.Images.Media.getBitmap(context.contentResolver, photoUri)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return image
}
/**
 * permite crear una uri para guardar la imagen
 * */
fun creaUri(context: Context): Uri? {
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, nombreArchivo())
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        put(MediaStore.Images.ImageColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }
    return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}
