@file:OptIn(ExperimentalUuidApi::class)

package com.ggardet.ai.service

import com.ggardet.ai.processor.CompressionDocumentPostProcessor
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Service
class VectorService(
    private val chatClientBuilder: ChatClient.Builder,
    private val vectorStore: VectorStore,
    private val conversationIdValue: String = Uuid.random().toString()
) : AiService {
    private val vectorAdvisor = RetrievalAugmentationAdvisor.builder()
        .queryExpander(
            // uses a large language model to expand a query into multiple semantically diverse variations to
            // capture different perspectives useful for retrieving additional contextual information and increasing
            // the chances of finding relevant results
            MultiQueryExpander.builder()
                .chatClientBuilder(chatClientBuilder.clone())
                .numberOfQueries(3)
                .build()
        )
        .queryTransformers(
            // uses a large language model to rewrite a user query to provide better results when querying a
            // target system, such as a vector store
            RewriteQueryTransformer.builder()
                .chatClientBuilder(chatClientBuilder.clone())
                .targetSearchSystem("vector store")
                .build(),
            // uses a large language model to translate a query to a target language that is supported by the
            // embedding model used to generate the document embeddings
            TranslationQueryTransformer.builder()
                .chatClientBuilder(chatClientBuilder.clone())
                .targetLanguage("english")
                .build(),
        )
        .documentRetriever(
            // retrieves documents from a vector store based on their similarity to the input query
            VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.5)
                .topK(3)
                .build()
        )
        .documentPostProcessors(
            // uses a large language model to compress a document content to reduce noise and redundancy
            // this is useful when the document content is too long and contains a lot of unnecessary information
            CompressionDocumentPostProcessor(chatClientBuilder)
        )
        .build()

    override fun query(query: String): Flux<String> =
        chatClientBuilder.build()
            .prompt()
            .advisors(vectorAdvisor)
            .advisors { it.param(CONVERSATION_ID, conversationIdValue) }
            .user(query)
            .stream()
            .content()
}
