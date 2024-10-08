package com.moronlu18.helloworldkotlin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


/**
 * Actividad principal que muestra dos textos y un botón que crea un
 * [Toast](https://developer.android.com/reference/android/widget/Toast?hl=en).
 * <ol>
 *     <li>Añadir dependencias al proyecto</li>
 *     <li>Crear un layout en XML</li>
 *     <li>Sincronizar el proyecto con Firebase</li>
 *     <li>Crear una excepción con throws</li>
 *     <li>Utilizar el registro de sucesos LogCat</li>
 *     <li>Generar la documentacion en formato html</li>
 * </ol>
 *
 * @author Lourdes Rodríguez Morón
 * @version 1.0
 * @constructor Create empty Main activity
 */
class MainActivity : AppCompatActivity() {

    private val TAG ="MainActivity"

    /**
     * On create de mi activuidad principal
     *
     * @param savedInstanceState
     * @throws RuntimeException para provocar un fallo en Firebase
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //TODO: Crear las variales fuera de la clase
        val tvGreating: TextView =
            findViewById(R.id.tvGreating)

        tvGreating.text = getString(R.string.tvGreatingTextFinal)

        val btnChangeGreating = findViewById<Button>(R.id.btSendMessage)
        btnChangeGreating.setOnClickListener {
            tvGreating.text = getString(R.string.tvGreatingTextFinal)

            Toast.makeText(this, "Y ahora con el Contexto ", Toast.LENGTH_SHORT).show()
            //throw RuntimeException("Aquí hay un gran error") // Force a crash
        }
        Log.d(TAG,"MainActivity-> onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"MainActivity-> onStart()")
    }


}