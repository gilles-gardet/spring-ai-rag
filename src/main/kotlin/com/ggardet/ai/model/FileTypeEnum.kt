package com.ggardet.ai.model

import com.ggardet.ai.model.FileTypeEnum.OTHER
import com.ggardet.ai.model.FileTypeEnum.entries
import org.apache.commons.io.FilenameUtils
import java.nio.file.Path

enum class FileTypeEnum(val extensions: List<String>) {
    HTML(listOf("htm", "html")),
    JSON(listOf("json")),
    MARKDOWN(listOf("md")),
    OTHER(listOf()),
    PDF(listOf("pdf")),
    TXT(listOf("txt")),
    XML(listOf("xml"));
}

fun fromFileExtension(extension: String): FileTypeEnum = entries.firstOrNull { entry ->
    val element = extension.lowercase()
    entry.extensions.contains(element)
} ?: OTHER

fun getFileExtension(path: Path): String = FilenameUtils.getExtension(path.toString())
