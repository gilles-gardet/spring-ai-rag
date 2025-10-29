package com.ggardet.ai.service

import reactor.core.publisher.Flux

fun interface AiService {
    fun query(query: String): Flux<String>
}
