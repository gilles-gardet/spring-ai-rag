package com.ggardet.ai.loader

import org.springframework.ai.document.Document
import org.springframework.ai.reader.markdown.MarkdownDocumentReader
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig
import org.springframework.core.io.Resource
import java.util.UUID

class AiMarkdownLoader: AiLoader {
    override fun load(resource: Resource, name: String?): List<Document> {
        val config = MarkdownDocumentReaderConfig.builder()
            .withHorizontalRuleCreateDocument(true)
            .withIncludeCodeBlock(false)
            .withIncludeBlockquote(false)
            .withAdditionalMetadata("filename", name ?: UUID.randomUUID().toString())
            .build()
        val markdownDocumentReader = MarkdownDocumentReader(resource, config)
        return markdownDocumentReader.get()
    }
}
