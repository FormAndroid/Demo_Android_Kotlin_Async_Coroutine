package be.bxl.formation.demo_android_kotlin_async_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var btnDemo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDemo = findViewById(R.id.btn_main_demo)
        btnDemo.setOnClickListener { demoContext() }

        // Pour utiliser les coroutines, il est necessaire d'ajouter au Grade :
        // 'org.jetbrains.kotlinx:kotlinx-coroutines-android:...'
    }

    private val scope: CoroutineScope = MainScope()

    fun demoScope() {
        Toast.makeText(applicationContext, "Start !", Toast.LENGTH_LONG).show()
        Log.i("DEMO_COROUTINES", "Start !")

        // Scope Global a TOUTE l'application (-> A vous de tout gérer)
        GlobalScope.launch {

        }

        // Scope principal, créer à l'aide de "MainScope()".
        // Ne pas oublier de l'annuler (scope.cancel()) dans le destroy.
        scope.launch {

        }

        // Scope lier au cycle de vie d'android
        // Necessite l'ajout au Grade de 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.1'
        lifecycleScope.launch {

            delay(10_000)
            interactwithUI()
        }

        Toast.makeText(applicationContext, "Fin !", Toast.LENGTH_LONG).show()
        Log.i("DEMO_COROUTINES", "Fin !")
    }

    fun demoContext() {
        Toast.makeText(applicationContext, "Start !", Toast.LENGTH_LONG).show()
        Log.i("DEMO_COROUTINES", "Start !")

        // Il existe 3 type de contexte utilisable via le Dispatcher :
        //  - Main:     Le thread principal d'android -> Pour tout se qui est interaction avec l'UI
        //  - Default : Optimisé pour effecter des traitement lourd sur le CPU
        //  - IO:       Optimiser pour interagir avec le disque (Ficher, SQLite) ou le réseau (Requete API)

        lifecycleScope.launch {
            // Pour selectionné le context désiré, il est necessaire "withContext()"

            withContext(Dispatchers.Default) {
                // -> Thread "main"
                delay(10_000)
                interactwithUI()
            }
        }

        Toast.makeText(applicationContext, "Fin !", Toast.LENGTH_LONG).show()
        Log.i("DEMO_COROUTINES", "Fin !")
    }

    suspend fun interactwithUI() {
        Log.i("DEMO_COROUTINES", "Action")

        // On change de context
        withContext(Dispatchers.Main) {
            Toast.makeText(applicationContext, "BOUM !", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}