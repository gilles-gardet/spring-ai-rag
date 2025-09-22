@file:OptIn(ExperimentalUuidApi::class)

package com.ggardet.ai.config

import com.ggardet.ai.retriever.SearchEngineDocumentRetriever
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.MessageWindowChatMemory
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.SystemPromptTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.web.client.RestClient
import kotlin.uuid.ExperimentalUuidApi


@Configuration
class AiConfig {
    @Bean
    fun ragPrompt(@Value("classpath:/prompt.st") prompt: Resource): Prompt? =
        SystemPromptTemplate(prompt).create()

    @Bean
    fun chatClientBuilder(
        chatModel: ChatModel,
        prompt: Prompt,
        messageChatMemoryAdvisor: MessageChatMemoryAdvisor,
        safeGuardAdvisor: SafeGuardAdvisor
    ): ChatClient.Builder {
        val systemMessage = prompt.contents
        return ChatClient.builder(chatModel)
            .defaultSystem(systemMessage)
            .defaultAdvisors(
                SimpleLoggerAdvisor(),
                messageChatMemoryAdvisor,
                safeGuardAdvisor
            )
    }

    @Bean
    fun safeGuardAdvisor(): SafeGuardAdvisor = SafeGuardAdvisor.builder()
        // should block any question related to broccolis
        .sensitiveWords(mutableListOf("broccoli"))
        .failureResponse("Sorry, I can't process that request.")
        .build()

    @Bean
    fun chatMemory(): ChatMemory = MessageWindowChatMemory.builder().build()

    @Bean
    fun messageChatMemoryAdvisor(chatMemory: ChatMemory): MessageChatMemoryAdvisor =
        MessageChatMemoryAdvisor.builder(chatMemory)
            .build()

    @Bean
    fun restClientBuilder(): RestClient.Builder = RestClient.builder()

    @Bean
    fun searchEngineDocumentRetriever(restClientBuilder: RestClient.Builder): SearchEngineDocumentRetriever =
        SearchEngineDocumentRetriever(restClientBuilder, 5)
}
