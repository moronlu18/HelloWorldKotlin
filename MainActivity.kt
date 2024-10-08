package com.moronlu18.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/**
 * Aplicación que muestra el típico ejemplo de Hola Mundo
 *
 *  1. Se ha visto cómo se crean los recursos en XML
 *  1. Se ha instanciado un objeto TextView @link TextView
 *  1. Se ha personalizado la imagen de la aplicación
 *
 */
class MainActivity : AppCompatActivity() {
    private lateinit var tvMessageStart: TextView
    private lateinit var tvMessageEnd: TextView
    private lateinit var btCrash: Button

    //Método que se llama en la creación de una Actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTextViewProperties()
    }
    
    private fun setTextViewProperties() {
        tvMessageStart = findViewById(R.id.tvMessageStart)
        tvMessageEnd = findViewById(R.id.tvMessageEnd)
        btCrash=findViewById(R.id.btCrash)
        tvMessageStart.setTextColor(ContextCompat.getColor(this, R.color.white))
        tvMessageEnd.setText(R.string.messageOptimist)
        btCrash.setOnClickListener {
            throw RuntimeException("¡Crash!")
        }

    }
}