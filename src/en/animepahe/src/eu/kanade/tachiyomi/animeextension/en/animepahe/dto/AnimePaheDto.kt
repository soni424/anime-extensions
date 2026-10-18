package eu.kanade.tachiyomi.animeextension.en.animepahe.dto

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto<T>(
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("last_page")
    val lastPage: Int,
    @EncodeDefault
    @SerialName("data")
    val items: List<T> = emptyList(),
)

@Serializable
data class LatestAnimeDto(
    @SerialName("anime_id") val id: Int,
    @SerialName("anime_session") val session: String,
    @SerialName("anime_title") val title: String,
    val snapshot: String,
    val fansub: String? = null,
)

@Serializable
data class SearchResultDto(
    val id: Int,
    val title: String,
    val poster: String,
    val session: String,
)

@Serializable
data class EpisodeDto(
    @SerialName("created_at") val createdAt: String,
    val session: String,
    @SerialName("episode") val episodeNumber: Float,
    @SerialName("anime_id") val animeId: Int,
)

@Serializable
data class AniZipResponseDto(
    val episodes: Map<String, AniZipEpisodeDto> = emptyMap(),
)

@Serializable
data class AniZipEpisodeDto(
    val title: AniZipTitleDto? = null,
    val overview: String? = null,
    val summary: String? = null,
    val image: String? = null,
) {
    val resolvedTitle: String?
        get() = title?.en?.takeIf { it.isNotBlank() }
            ?: title?.xJat?.takeIf { it.isNotBlank() }

    val resolvedSummary: String?
        get() = overview?.takeIf { it.isNotBlank() }
            ?: summary?.takeIf { it.isNotBlank() }
}

@Serializable
data class AniZipTitleDto(
    val en: String? = null,
    @SerialName("x-jat") val xJat: String? = null,
)
