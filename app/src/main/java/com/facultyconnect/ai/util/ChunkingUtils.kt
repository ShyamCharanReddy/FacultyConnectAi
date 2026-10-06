package com.facultyconnect.ai.util

data class TextChunk(
    val index: Int,
    val text: String,
    val charStart: Int,
    val charEnd: Int
)

object ChunkingUtils {
    /**
     * Splits long text (lecture slides, notes, syllabus) into overlapping chunks.
     * Default chunk size: 650 characters (between 500 and 800), with 100-character overlap.
     */
    fun chunkText(
        text: String,
        chunkSize: Int = 650,
        overlap: Int = 100
    ): List<TextChunk> {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return emptyList()
        if (trimmed.length <= chunkSize) {
            return listOf(TextChunk(index = 0, text = trimmed, charStart = 0, charEnd = trimmed.length))
        }

        val chunks = mutableListOf<TextChunk>()
        var start = 0
        var chunkIndex = 0

        while (start < trimmed.length) {
            var end = (start + chunkSize).coerceAtMost(trimmed.length)

            // Try to break chunk at sentence or word boundary if not at end of text
            if (end < trimmed.length) {
                val boundaryPeriod = trimmed.lastIndexOf('.', end)
                val boundaryNewline = trimmed.lastIndexOf('\n', end)
                val sentenceBreak = maxOf(boundaryPeriod, boundaryNewline)
                if (sentenceBreak > start + (chunkSize / 2)) {
                    end = sentenceBreak + 1
                } else {
                    val spaceBreak = trimmed.lastIndexOf(' ', end)
                    if (spaceBreak > start + (chunkSize / 2)) {
                        end = spaceBreak
                    }
                }
            }

            val chunkContent = trimmed.substring(start, end).trim()
            if (chunkContent.isNotEmpty()) {
                chunks.add(
                    TextChunk(
                        index = chunkIndex++,
                        text = chunkContent,
                        charStart = start,
                        charEnd = end
                    )
                )
            }

            if (end >= trimmed.length) break
            start = (end - overlap).coerceAtLeast(start + 1)
        }

        return chunks
    }
}