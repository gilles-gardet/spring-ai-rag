package com.ggardet.ai.command

import com.ggardet.ai.service.AiService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class AiCommand(val aiService: AiService) {

    @ShellMethod(key = ["ask"], value = "ask a question to the AI chat bot")
    fun askQuestion(query: String) = aiService.chat(query)
}
