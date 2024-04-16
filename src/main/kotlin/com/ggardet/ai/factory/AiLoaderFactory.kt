package com.ggardet.ai.factory

import com.ggardet.ai.loader.AiJsonLoader
import com.ggardet.ai.loader.AiLoader
import com.ggardet.ai.loader.AiMarkdownLoader
import com.ggardet.ai.loader.AiOtherLoader
import com.ggardet.ai.loader.AiPdfLoader
import com.ggardet.ai.loader.AiTextLoader
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
