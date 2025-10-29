@file:OptIn(ExperimentalUuidApi::class)

package com.ggardet.ai.service

import com.ggardet.ai.retriever.SearchEngineDocumentRetriever
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Service
class SearchService(
    private var chatClientBuilder: ChatClient.Builder,
    private val conversationIdValue: String = Uuid.random().toString(),
    searchEngineDocumentRetriever: SearchEngineDocumentRetriever
) : AiService {
    val searchAdvisor = RetrievalAugmentationAdvisor.builder()
        .queryTransformers(
            // uses a large language model to rewrite a user query to provide better results when querying a
            // target system, such as a vector store or a web search engine
            RewriteQueryTransformer.builder()
                .chatClientBuilder(chatClientBuilder.clone())
                .targetSearchSystem("web search engine")
                .build()
        )
        .documentRetriever(searchEngineDocumentRetriever)
        .queryAugmenter(
            // by default, the RetrievalAugmentationAdvisor does not allow the retrieved context to be empty
            // when that happens, it instructs the model not to answer the user query
            // you can allow empty context as follows
            ContextualQueryAugmenter.builder()
                .allowEmptyContext(false)
                .build()
        )
        .build()

    override fun query(query: String): Flux<String> =
        chatClientBuilder.build()
            .prompt()
            .advisors(searchAdvisor)
            .advisors { it.param(CONVERSATION_ID, conversationIdValue) }
            .user(query)
            .stream()
            .content()
}
