package com.luizafmartinez.m28_getimagesapi

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luizafmartinez.m28_getimagesapi.api.RetrofitCustom
import com.luizafmartinez.m28_getimagesapi.api.model.Resultado
import com.luizafmartinez.m28_getimagesapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var galeriaAdapter: GaleriaAdapter

    private val imgurAPI by lazy {
        RetrofitCustom.ImgurAPI
    }

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        galeriaAdapter = GaleriaAdapter()

        /*
        galeriaAdapter.adicionarLista(
            listOf(
                "https://conteudo.imguol.com.br/c/entretenimento/36/2022/05/22/gata-tricolor-gato-gatos-1653265224214_v2_900x506.jpg",
                "https://conteudo.imguol.com.br/c/entretenimento/36/2022/05/22/gata-tricolor-gato-gatos-1653265224214_v2_900x506.jpg",
                "https://conteudo.imguol.com.br/c/entretenimento/36/2022/05/22/gata-tricolor-gato-gatos-1653265224214_v2_900x506.jpg",
                "https://conteudo.imguol.com.br/c/entretenimento/36/2022/05/22/gata-tricolor-gato-gatos-1653265224214_v2_900x506.jpg",
                "https://conteudo.imguol.com.br/c/entretenimento/36/2022/05/22/gata-tricolor-gato-gatos-1653265224214_v2_900x506.jpg",
                "https://conteudo.imguol.com.br/c/entretenimento/36/2022/05/22/gata-tricolor-gato-gatos-1653265224214_v2_900x506.jpg"
            )
        )
        */

        binding.rvGaleria.adapter = galeriaAdapter

        binding.rvGaleria.layoutManager = GridLayoutManager(
            this,
            3,
            RecyclerView.VERTICAL,
            false
        )
    }

    override fun onStart() {
        super.onStart()
        recuperarImagensAPI()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun recuperarImagensAPI() {

        job = CoroutineScope(Dispatchers.IO).launch {

            var response: Response<Resultado>? = null  //Vem de ImgurAPI.kt

            try {
                response = imgurAPI.pesquisarImagensGaleria("cats")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (response != null && response.isSuccessful) {

                val resultado = response.body()

                if (resultado != null) {

                    val lista = resultado.data

                    val listaUrlImagens = mutableListOf<String>()

                    lista.forEach { dados ->
                        val imagem = dados.images[0]
                        val tipo = imagem.type
                        if (tipo == "image/jpeg" || tipo == "image/png") {
                            listaUrlImagens.add(imagem.link)
                        }
                    }

                    withContext(Dispatchers.Main) {
                        galeriaAdapter.adicionarLista(listaUrlImagens)
                    }
                }
            } else {
                Log.i("teste_galeria", "Erro ao recuperar imagens")
            }
        }
    }
}