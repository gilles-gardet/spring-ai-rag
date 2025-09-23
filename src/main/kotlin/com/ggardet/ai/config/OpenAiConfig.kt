package com.ggardet.ai.config

import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.OpenAiEmbeddingModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Configuration
@Profile("openai")
class OpenAiConfig {

    @Bean
    @Primary
    fun primaryChatModel(openAiChatModel: OpenAiChatModel): ChatModel = openAiChatModel

    @Bean
    @Primary
    fun primaryEmbeddingModel(openAiEmbeddingModel: OpenAiEmbeddingModel): EmbeddingModel = openAiEmbeddingModel
}