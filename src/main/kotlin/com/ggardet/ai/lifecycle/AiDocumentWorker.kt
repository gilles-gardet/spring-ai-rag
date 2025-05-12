package com.ggardet.ai.lifecycle

import com.ggardet.ai.factory.AiLoaderFactory
import com.ggardet.ai.factory.AiLoader
import com.ggardet.ai.model.FileTypeEnum
import com.ggardet.ai.model.fromFileExtension
import com.ggardet.ai.model.getFileExtension
import jakarta.annotation.PostConstruct
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.name

@Component
class AiDocumentWorker(private val vectorStore: VectorStore) {
    @Value("classpath:/documents/")
    private lateinit var resource: Resource

    @PostConstruct
    fun loadDocuments() {
        resource.file.toPath().let { directoryPath ->
            Files.list(directoryPath)
                .filter { Files.isRegularFile(it) }
                .forEach { extractDocuments(it) }
        }
    }

    private fun extractDocuments(path: Path) {
        val fileExtension: String = getFileExtension(path.fileName)
        val fileType: FileTypeEnum = fromFileExtension(fileExtension)
        val fileLoader: AiLoader = AiLoaderFactory().create(fileType)
        val inputStream = Files.newInputStream(path)
        val inputStreamResource = InputStreamResource(inputStream)
        fileLoader.load(inputStreamResource, path.name).let { documents ->
            val splitter = TokenTextSplitter()
            val splitDocuments = splitter.apply(documents)
            vectorStore.add(splitDocuments)
        }
    }
}
