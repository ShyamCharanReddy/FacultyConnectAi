package com.facultyconnect.ai.domain.model

data class LectureChunk(
    val id: String,
    val courseCode: String,
    val lectureTitle: String,
    val content: String,
    val embedding: List<Float> = emptyList()
)
