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
 * @author Lourdes Rodríguez Morón
 * @version 1.0
 * @constructor Create empty Main activity
 */

class MainActivity : AppCompatActivity() {

    //TODO ámbito de las variables
    private val TAG = "MainActivity"
    lateinit var message: String

    /*
    Opción 1: Ineficiente
    private val tvGreating: TextView by lazy { findViewById(R.id.tvGreating) }
    private val btnChangeGreating by lazy {
        findViewById<Button>(R.id.btSendMessage)
    }*/

    /*
    Opción 2: View Binding
     */
    private lateinit var binding: ActivityMainBinding



    /**
     * On create de mi activuidad principal
     *
     * @param savedInstanceState
     * @throws RuntimeException para provocar un fallo en Firebase
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