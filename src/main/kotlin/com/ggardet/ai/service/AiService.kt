package com.ggardet.ai.service

import com.ggardet.ai.tool.repository.AddressRepository
import com.ggardet.ai.tool.repository.PersonRepository
import com.ggardet.ai.tool.service.AddressTool
import com.ggardet.ai.tool.service.PersonTool
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.stereotype.Service

@Service
class AiService(
    private val chatClientBuilder: ChatClient.Builder,
    private val prompt: Prompt,
    private val addressRepository: AddressRepository,
    private val personRepository: PersonRepository
) {
    fun queryRagDocuments(query: String): String? =
        chatClientBuilder.build()
            .prompt(prompt)
            .user(query)
            .call()
            .content()

    fun queryDatabaseTools(query: String): String? {
        val addressTool = AddressTool(addressRepository)
        val personTool = PersonTool(personRepository)
        return chatClientBuilder.build()
            .prompt(prompt)
            .tools(addressTool, personTool)
            .user(query)
            .call()
            .content()
    }
}
