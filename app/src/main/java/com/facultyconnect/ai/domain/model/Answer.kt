package com.facultyconnect.ai.domain.model

data class Answer(
    val id: String,
    val questionId: String,
    val authorName: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isAiDraft: Boolean = false,
    val isApproved: Boolean = false,
    val citedSources: List<String> = emptyList()
)
