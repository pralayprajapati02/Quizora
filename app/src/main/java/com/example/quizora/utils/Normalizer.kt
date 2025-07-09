package com.example.quizora.utils

import androidx.core.text.HtmlCompat

class Normalizer {
    fun normalizeHtmlToString(raw: String): String {
        return HtmlCompat.fromHtml(raw, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }

    fun normalizeHtmlMutableList(rawList: MutableList<List<String>>): MutableList<List<String>> {
        return rawList.map { innerList ->
            innerList.map { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString() }
        }.toMutableList()
    }

    fun cleanCategoryNames(categories: List<String>): List<String> {
        return categories.map { rawCategory ->
            var decoded = HtmlCompat.fromHtml(rawCategory, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

            decoded = when {
                decoded.startsWith("Entertainment:") -> decoded.replace("Entertainment:", "").trim()
                decoded.startsWith("Science:") -> decoded.replace("Science:", "").trim()
                else -> decoded.trim()
            }

            if (decoded.startsWith("Japanese ")){
                decoded = decoded.replace("Japanese", "").trim()
            }

            decoded

        }
    }
}