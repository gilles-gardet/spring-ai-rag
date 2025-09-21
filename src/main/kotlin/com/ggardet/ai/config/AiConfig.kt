package com.ggardet.ai.config

import com.ggardet.ai.retriever.SearchEngineDocumentRetriever
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.SystemPromptTemplate
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.web.client.RestClient


@Configuration
class AiConfig {
    @Bean
    fun ragPrompt(@Value("classpath:/prompt.st") prompt: Resource): Prompt =
        SystemPromptTemplate(prompt).create()

    @Bean
    fun chatClientBuilder(
        chatModel: ChatModel,
        @Qualifier("webRagAdvisor") webRagAdvisor: RetrievalAugmentationAdvisor,
        @Qualifier("vectorRagAdvisor") vectorRagAdvisor: RetrievalAugmentationAdvisor,
    ): ChatClient.Builder = ChatClient.builder(chatModel).defaultAdvisors(webRagAdvisor, vectorRagAdvisor)

    @Bean("webRagAdvisor")
    fun webRagAdvisor(
        chatClientBuilder: ChatClient.Builder,
        searchEngineDocumentRetriever: SearchEngineDocumentRetriever
    ): RetrievalAugmentationAdvisor =
        RetrievalAugmentationAdvisor.builder()
            .queryTransformers(
                RewriteQueryTransformer.builder()
                    .chatClientBuilder(chatClientBuilder.clone())
                    .targetSearchSystem("web search engine")
                    .build()
            )
            .documentRetriever(searchEngineDocumentRetriever)
            .queryAugmenter(
                ContextualQueryAugmenter.builder()
                    .allowEmptyContext(false)
                    .build()
            )
            .build()

    @Bean("vectorRagAdvisor")
    fun vectorRagAdvisor(vectorStore: VectorStore): RetrievalAugmentationAdvisor =
        RetrievalAugmentationAdvisor.builder()
            .documentRetriever(
                VectorStoreDocumentRetriever.builder()
                    .vectorStore(vectorStore)
                    .similarityThreshold(0.5)
                    .topK(3)
                    .build()
            )
            .build()

    @Bean
    fun searchEngineDocumentRetriever(restClientBuilder: RestClient.Builder): SearchEngineDocumentRetriever =
        SearchEngineDocumentRetriever(restClientBuilder, 10)
}
