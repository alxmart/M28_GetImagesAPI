package com.luizafmartinez.m28_getimagesapi.api.model

data class Resultado(
    val `data`: List<Data>,
    val status: Int,
    val success: Boolean
)