package com.moronlu18.helloworldkotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.moronlu18.helloworldkotlin.databinding.ActivityMainBinding


/**
 * Actividad principal que muestra dos textos y un botón que crea un
 * [Toast](https://developer.android.com/reference/android/widget/Toast?hl=en).
 *
 * Esta actividad demuestra conceptos básicos de desarrollo Android:
 * <ol>
 *     <li>Añadir dependencias al proyecto</li>
 *     <li>Crear un layout en XML</li>
 *     <li>Sincronizar el proyecto con Firebase. Configuración de dependencias.</li>
 *     <li>Crear una excepción con throws</li>
 *     <li>Utilizar el registro de sucesos LogCat</li>
 *     <li>Generar la documentacion en formato html</li>
 *     <li>Inicializacion tardía (lateinit) y perezosa (by lazy)</li>
 *     <li>A depurar con puntos de interrupción</li>
 *     <li>Crear regiones de métodos que tienen una relación</li>
 * </ol>
 *
 * Utiliza [View Binding](https://developer.android.com/topic/libraries/view-binding) para acceder a las vistas del layout.
 *
 * @author Lourdes Rodríguez Morón
 * @version 1.0
 * @constructor Create empty Main activity
 * @see AppCompatActivity
 * @see ActivityMainBinding
 */

class MainActivity : AppCompatActivity() {

    /**
     * Etiqueta para los logs de depuración.
     */
    private val TAG = "MainActivity"

    /**
     * Mensaje que se muestra en la interfaz.
     * Se inicializa de forma tardía con [lateinit].
     */
    lateinit var message: String


    /**
     * Referencia al binding de la actividad.
     * Permite acceder a las vistas del layout de forma segura.
     *
     * @see ActivityMainBinding
     */
    private lateinit var binding: ActivityMainBinding



    /**
     * Se llama cuando se crea la actividad por primera vez.
     * Inicializa la interfaz de usuario y configura los listeners de eventos.
     *
     * @param savedInstanceState Si la actividad se está recreando después de un cierre
     *                           previo, este contiene el estado guardado.
     * @throws RuntimeException Se lanza intencionalmente para probar Firebase Crashlytics.
     * @see [Documentación oficial de Android](https://developer.android.com/guide/components/activities/activity-lifecycle)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Opción 1: setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        message = "Hola Mundo"
        /**
         * Opción 1:
         *  tvGreating.text = getString(R.string.tvGreatingTextFinal)
         *         btnChangeGreating.setOnClickListener {
         *             tvGreating.text = getString(R.string.tvGreatingTextFinal)
         *             //Toast.makeText(this, "Y ahora con el Contexto ", Toast.LENGTH_SHORT).show()
         *             throw RuntimeException("Aquí hay un gran error") // Force a crash
         *         }
         */


        //Opcción 2: View Binding
        binding.tvGreating. text = getString(R.string.tvGreatingTextFinal)
        binding.btSendMessage.setOnClickListener {
            binding.tvGreating.text = getString(R.string.tvGreatingTextFinal)
            //Toast.makeText(this, "Y ahora con el Contexto ", Toast.LENGTH_SHORT).show()
            throw RuntimeException("Aquí hay un gran error") // Force a crash
        }

        Log.d(TAG, "MainActivity-> onCreate()")
    }

    //region Ciclo de Vida de una Activity
    /**
     * Se llama cuando la actividad se vuelve visible para el usuario.
     * Registra un mensaje en LogCat para seguimiento del ciclo de vida.
     *
     * @see [Documentación oficial de Android](https://developer.android.com/guide/components/activities/activity-lifecycle)
     */
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity-> onStart()")
        //TODO comentari
        Log.d(TAG, "Texto del saludo: ${binding.tvGreating}.text")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity-> onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity-> onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity-> onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity-> onDestroy()")
    }

    //endregion

}