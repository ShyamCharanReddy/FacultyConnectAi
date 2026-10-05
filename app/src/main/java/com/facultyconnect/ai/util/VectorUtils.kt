package com.facultyconnect.ai.util

import kotlin.math.sqrt

object VectorUtils {
    /**
     * Computes the cosine similarity between two FloatArray vectors.
     * Returns a float value between -1.0 and 1.0 (or 0.0 for empty/mismatched sizes).
     */
    fun cosineSimilarity(vectorA: FloatArray, vectorB: FloatArray): Float {
        if (vectorA.size != vectorB.size || vectorA.isEmpty()) return 0.0f
        var dotProduct = 0.0f
        var normA = 0.0f
        var normB = 0.0f
        for (i in vectorA.indices) {
            dotProduct += vectorA[i] * vectorB[i]
            normA += vectorA[i] * vectorA[i]
            normB += vectorB[i] * vectorB[i]
        }
        val denominator = sqrt(normA.toDouble()) * sqrt(normB.toDouble())
        return if (denominator == 0.0) 0.0f else (dotProduct / denominator).toFloat()
    }

    /**
     * Overload for List<Float> cosine similarity computation.
     */
    fun cosineSimilarity(vectorA: List<Float>, vectorB: List<Float>): Float {
        if (vectorA.size != vectorB.size || vectorA.isEmpty()) return 0.0f
        var dotProduct = 0.0f
        var normA = 0.0f
        var normB = 0.0f
        for (i in vectorA.indices) {
            val a = vectorA[i]
            val b = vectorB[i]
            dotProduct += a * b
            normA += a * a
            normB += b * b
        }
        val denominator = sqrt(normA.toDouble()) * sqrt(normB.toDouble())
        return if (denominator == 0.0) 0.0f else (dotProduct / denominator).toFloat()
    }

    /**
     * Normalizes a float vector to unit length (L2 norm).
     */
    fun normalize(vector: FloatArray): FloatArray {
        var norm = 0.0f
        for (v in vector) {
            norm += v * v
        }
        val length = sqrt(norm.toDouble()).toFloat()
        if (length == 0.0f) return vector
        return FloatArray(vector.size) { i -> vector[i] / length }
    }

    /**
     * Generates a deterministic normalized 768-dimensional float embedding
     * from text for offline fallback and unit testing.
     */
    fun generateDeterministicEmbedding(text: String, dimension: Int = 768): List<Float> {
        val array = FloatArray(dimension)
        if (text.isBlank()) return array.toList()

        val tokens = text.lowercase()
            .replace(Regex("[^a-z0-9 ]"), " ")
            .split(Regex("\\s+"))
            .filter { it.isNotBlank() }

        for (token in tokens) {
            val hash = token.hashCode()
            val baseIdx = Math.floorMod(hash, dimension)
            for (step in 0 until 5) {
                val idx = (baseIdx + step * 31) % dimension
                array[idx] += 1.0f / (step + 1)
            }
        }

        // Also add character trigrams for substring and fuzzy matching
        val cleaned = text.lowercase().replace(Regex("\\s+"), " ")
        if (cleaned.length >= 3) {
            for (i in 0 until cleaned.length - 2) {
                val tri = cleaned.substring(i, i + 3)
                val triHash = tri.hashCode()
                val idx = Math.floorMod(triHash, dimension)
                array[idx] += 0.5f
            }
        }

        return normalize(array).toList()
    }
}
