package com.ggardet.ai.factory

import com.ggardet.ai.model.FileTypeEnum
import org.springframework.ai.document.Document
import org.springframework.ai.reader.ExtractedTextFormatter
import org.springframework.ai.reader.JsonReader
import org.springframework.ai.reader.TextReader
import org.springframework.ai.reader.markdown.MarkdownDocumentReader
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.ai.reader.tika.TikaDocumentReader
import org.springframework.core.io.Resource
import java.util.UUID

class AiLoaderFactory {
    fun create(fileTypeEnum: FileTypeEnum): AiLoader = when (fileTypeEnum) {
        FileTypeEnum.PDF -> AiPdfLoader()
        FileTypeEnum.MARKDOWN -> AiMarkdownLoader()
        FileTypeEnum.TXT -> AiTextLoader()
        FileTypeEnum.JSON -> AiJsonLoader()
        FileTypeEnum.HTML, FileTypeEnum.XML, FileTypeEnum.OTHER -> AiOtherLoader()
    }
}

fun interface AiLoader {
    fun load(resource: Resource, name: String?): List<Document>
}

class AiJsonLoader : AiLoader {
    override fun load(resource: Resource, name: String?): List<Document> = JsonReader(resource).get()
}

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

class AiPdfLoader : AiLoader {
    override fun load(resource: Resource, name: String?): List<Document> {
        val extractedTextFormatter = ExtractedTextFormatter.builder()
            .withNumberOfTopTextLinesToDelete(0)
            .withLeftAlignment(true)
            .build()
        val pdfDocumentReaderConfig = PdfDocumentReaderConfig.builder()
            .withPageExtractedTextFormatter(extractedTextFormatter)
            .withPagesPerDocument(1)
            .withPageTopMargin(0)
            .build()
        val pdfReader = PagePdfDocumentReader(resource, pdfDocumentReaderConfig)
        return pdfReader.read()
    }
}

class AiTextLoader : AiLoader {
    override fun load(resource: Resource, name: String?): List<Document> = TextReader(resource).get()
}


class AiOtherLoader : AiLoader {
    override fun load(resource: Resource, name: String?): List<Document> = TikaDocumentReader(resource).get()
}
