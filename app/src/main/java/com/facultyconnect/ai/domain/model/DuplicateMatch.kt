package com.facultyconnect.ai.domain.model

data class DuplicateMatch(
    val matchedQuestion: Question,
    val similarityScore: Float,
    val existingAnswer: Answer? = null
)
