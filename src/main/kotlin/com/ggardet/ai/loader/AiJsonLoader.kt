package com.ggardet.ai.loader

import org.springframework.ai.document.Document
import org.springframework.ai.reader.JsonReader
import org.springframework.core.io.Resource

class AiJsonLoader : AiLoader {
    override fun load(resource: Resource, name: String?): List<Document> = JsonReader(resource).get()
}
