package com.ggardet.ai.processor

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.ChatOptions
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.document.Document
import org.springframework.ai.rag.Query
import org.springframework.ai.rag.postretrieval.document.DocumentPostProcessor
import org.springframework.ai.rag.util.PromptAssert

class CompressionDocumentPostProcessor(
    chatClientBuilder: ChatClient.Builder,
    private val promptTemplate: PromptTemplate = DEFAULT_PROMPT_TEMPLATE
) : DocumentPostProcessor {

    companion object {
        private val DEFAULT_PROMPT_TEMPLATE = PromptTemplate(
            """
            Given the following contextual information and input query, your task is to synthesize
            a compressed version of the context that is relevant to answer the input query,
            reducing noise and redundancy.

            Contextual information:
            {context}

            User query:
            {query}

            Compressed contextual information:
            """.trimIndent()
        )
    }

    private val chatClient: ChatClient = chatClientBuilder.build()

    init {
        PromptAssert.templateHasRequiredPlaceholders(promptTemplate, "context", "query")
    }

    override fun process(query: Query, documents: List<Document>): List<Document> =
        documents.takeIf { it.isNotEmpty() }?.map { document ->
            document.mutate()
                .text(
                    chatClient.prompt()
                        .user { user ->
                            user.text(promptTemplate.template)
                                .param("context", document.text ?: "")
                                .param("query", query.text())
                        }
                        .options(
                            ChatOptions.builder()
                                .temperature(0.2)
                                .build()
                        )
                        .call()
                        .content()
                )
                .build()
        } ?: documents
}
