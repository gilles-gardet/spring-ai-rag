package com.ggardet.ai.loader

import org.springframework.ai.document.Document
import org.springframework.core.io.Resource

fun interface AiLoader {
    fun load(resource: Resource, name: String?): List<Document>
}
