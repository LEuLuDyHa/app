package com.github.leuludyha.domain.useCase

import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.CoverSize
import com.github.leuludyha.domain.repository.LibraryRepository

class GetCoverBitmapLocallyUseCase(private val libraryRepository: LibraryRepository) {
    operator fun invoke(cover: Cover, coverSize: CoverSize) =
        libraryRepository.getCoverBitmap(cover, coverSize)
}