package com.ggardet.ai.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.SystemPromptTemplate
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource


@Configuration
class AiConfig {

    @Bean
    fun ragPrompt(@Value("classpath:/prompt.st") prompt: Resource): Prompt =
        SystemPromptTemplate(prompt).create()

    @Bean
    fun chatClientBuilder(chatModel: ChatModel, vectorStore: VectorStore): ChatClient.Builder {
        val searchRequest = SearchRequest.builder()
            .similarityThreshold(0.8)
            .topK(3)
            .build()
        val questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
            .searchRequest(searchRequest)
            .build()
        return ChatClient.builder(chatModel).defaultAdvisors(questionAnswerAdvisor)
    }
}
