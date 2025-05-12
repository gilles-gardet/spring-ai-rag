package com.ggardet.ai.factory

import com.ggardet.ai.model.FileTypeEnum

class AiLoaderFactory {
    fun create(fileTypeEnum: FileTypeEnum): AiLoader = when (fileTypeEnum) {
        FileTypeEnum.PDF -> AiPdfLoader()
        FileTypeEnum.MARKDOWN -> AiMarkdownLoader()
        FileTypeEnum.TXT -> AiTextLoader()
        FileTypeEnum.JSON -> AiJsonLoader()
        FileTypeEnum.HTML, FileTypeEnum.XML, FileTypeEnum.OTHER -> AiOtherLoader()
    }
}
