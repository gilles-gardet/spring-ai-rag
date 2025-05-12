package com.ggardet.ai.command

import com.ggardet.ai.service.AiService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class AiCommand(val aiService: AiService) {

    @ShellMethod(key = ["rag"], value = "ask a question to the AI about the documents (RAG)")
    fun queryRagDocuments(query: String) = aiService.queryRagDocuments(query)

    @ShellMethod(key = ["tool"], value = "interact with the database using AI (Tool)")
    fun queryDatabaseTools(query: String) = aiService.queryDatabaseTools(query)
}
