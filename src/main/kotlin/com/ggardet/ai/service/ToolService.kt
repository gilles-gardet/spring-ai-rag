@file:OptIn(ExperimentalUuidApi::class)

package com.ggardet.ai.service

import com.ggardet.ai.tool.service.AddressTool
import com.ggardet.ai.tool.service.PersonTool
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Service
class ToolService(
    private val chatClientBuilder: ChatClient.Builder,
    private val addressTool: AddressTool,
    private val personTool: PersonTool,
    private val conversationIdValue: String = Uuid.random().toString()
) : AiService {
    override fun query(query: String): Flux<String> =
        chatClientBuilder.build()
            .prompt()
            .advisors { it.param(CONVERSATION_ID, conversationIdValue) }
            .tools(addressTool, personTool)
            .user(query)
            .stream()
            .content()
}
