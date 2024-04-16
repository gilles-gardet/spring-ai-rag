package com.ggardet.ai.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class AiConfig {

    @Bean
    fun restClientBuilder(): RestClient.Builder = RestClient.builder()

    @Bean
    fun chatClient(chatClientBuilder: ChatClient.Builder): ChatClient = chatClientBuilder.build()
}
