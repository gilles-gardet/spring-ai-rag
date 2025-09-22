package com.ggardet.ai.service

fun interface AiService {
    fun query(query: String): String?
}
