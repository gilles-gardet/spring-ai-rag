package com.ggardet.ai.retriever

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.ai.document.Document
import org.springframework.ai.rag.Query
import org.springframework.ai.rag.retrieval.search.DocumentRetriever
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestClient

private const val TAVILY_API_URL = "https://api.tavily.com/search"

class SearchEngineDocumentRetriever(
    restClientBuilder: RestClient.Builder,
    private val maxResults: Int
) : DocumentRetriever {

    private val restClient: RestClient = restClientBuilder
        .baseUrl(TAVILY_API_URL)
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${System.getenv("TAVILY_API_KEY")}")
        .build()

    override fun retrieve(query: Query): List<Document> {
        val response = restClient.post()
            .body(TavilySearchRequest(query.text(), "advanced", maxResults))
            .retrieve()
            .body(TavilySearchResponse::class.java)
        return response?.results
            ?.map { result ->
                Document.builder()
                    .text(result.content)
                    .metadata("title", result.title ?: "No title")
                    .metadata("url", result.url ?: "No url")
                    .score(result.score)
                    .build()
            } ?: emptyList()
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    internal data class TavilySearchRequest(
        val query: String,
        val searchDepth: String,
        val maxResults: Int
    )

    internal data class TavilySearchResponse(val results: List<Result>?)

    internal data class Result(
        val title: String?,
        val url: String?,
        val content: String?,
        val score: Double?
    )
}
