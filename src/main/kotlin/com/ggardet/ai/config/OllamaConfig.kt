package com.ggardet.ai.config

import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.ai.ollama.OllamaEmbeddingModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Configuration
@Profile("ollama")
class OllamaConfig {

    @Bean
    @Primary
    fun primaryChatModel(ollamaChatModel: OllamaChatModel): ChatModel = ollamaChatModel

    @Bean
    @Primary
    fun primaryEmbeddingModel(ollamaEmbeddingModel: OllamaEmbeddingModel): EmbeddingModel = ollamaEmbeddingModel
}