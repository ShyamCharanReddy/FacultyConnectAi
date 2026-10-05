package com.facultyconnect.ai.domain.model

enum class QuestionStatus {
    PENDING_REVIEW,
    AI_DRAFT_READY,
    RESOLVED
}

data class Question(
    val id: String,
    val courseCode: String,
    val title: String,
    val body: String,
    val authorName: String = "Student",
    val createdAt: Long = System.currentTimeMillis(),
    val status: QuestionStatus = QuestionStatus.PENDING_REVIEW,
    val embedding: List<Float> = emptyList()
)
