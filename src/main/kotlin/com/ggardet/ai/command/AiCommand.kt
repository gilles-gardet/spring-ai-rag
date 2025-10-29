package com.ggardet.ai.command

import com.ggardet.ai.service.BasicService
import com.ggardet.ai.service.SearchService
import com.ggardet.ai.service.ToolService
import com.ggardet.ai.service.VectorService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class AiCommand(
    val basicService: BasicService,
    val vectorService: VectorService,
    val toolService: ToolService,
    val searchService: SearchService
) {

    @ShellMethod(key = ["basic"], value = "ask a question to the LLM")
    fun queryLlm(query: String) = basicService.query(query)
        .doOnNext { chunk -> print(chunk) }
        .blockLast()

    @ShellMethod(key = ["vector"], value = "ask the LLM to find answers using the vector store")
    fun queryVector(query: String) = vectorService.query(query)
        .doOnNext { chunk -> print(chunk) }
        .blockLast()

    @ShellMethod(key = ["tool"], value = "ask the LLM to use tools to answer the question")
    fun queryTool(query: String) = toolService.query(query)
        .doOnNext { chunk -> print(chunk) }
        .blockLast()

    @ShellMethod(key = ["web"], value = "ask the LLM to search answers through the web")
    fun queryWeb(query: String) = searchService.query(query)
        .doOnNext { chunk -> print(chunk) }
        .blockLast()
}
