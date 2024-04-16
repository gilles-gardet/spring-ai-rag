package com.ggardet.ai.loader

import org.springframework.ai.document.Document
import org.springframework.ai.reader.ExtractedTextFormatter
import org.springframework.ai.reader.pdf.PagePdfDocumentReader
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig
import org.springframework.core.io.Resource

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
