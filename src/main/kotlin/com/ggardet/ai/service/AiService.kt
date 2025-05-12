package com.ggardet.ai.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class AiService(
    @Value("classpath:/prompt.st")
    private val llmPrompt: Resource,
    private val chatClient: ChatClient,
    private val vectorStore: VectorStore
) {
    fun chat(query: String): String? {
        val documents = searchDocuments(query)
        val prompt = createPrompt(query, documents.orEmpty())
        return chatClient.prompt(prompt).call().content()
    }

    fun createPrompt(query: String, documents: List<Document>): Prompt {
        val context = mapOf("query" to query, "documents" to documents)
        return PromptTemplate(this.llmPrompt).create(context)
    }

    fun searchDocuments(query: String): List<Document>? {
        val searchRequest = SearchRequest.builder().query(query).topK(2).build()
        return vectorStore.similaritySearch(searchRequest)
    }
}
