package com.ggardet.ai.loader

import org.springframework.ai.document.Document
import org.springframework.ai.reader.TextReader
import org.springframework.core.io.Resource

class AiTextLoader : AiLoader {
    override fun load(resource: Resource, name: String?): List<Document> = TextReader(resource).get()
}
