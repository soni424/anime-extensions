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
    // Audio of the episode's main upload; stays "jpn" even when an English dub is also uploaded.
    val audio: String? = null,
)

@Serializable
class AnimeScheduleResponseDto(
    val anime: List<AnimeScheduleAnimeDto> = emptyList(),
)

@Serializable
class AnimeScheduleAnimeDto(
    val dubPremier: String? = null,
)

/** Episode title, summary and thumbnail from a metadata source, keyed elsewhere by episode number. */
class EpisodeMeta(
    val title: String?,
    val summary: String?,
    val image: String?,
)

@Serializable
class AniZipResponseDto(
    val episodes: Map<String, AniZipEpisodeDto> = emptyMap(),
    val mappings: AniZipMappingsDto? = null,
)

@Serializable
class AniZipMappingsDto(
    @SerialName("anilist_id") val anilistId: Int? = null,
    @SerialName("kitsu_id") val kitsuId: Int? = null,
)

@Serializable
class AniZipEpisodeDto(
    private val title: AniZipTitleDto? = null,
    private val overview: String? = null,
    private val summary: String? = null,
    private val image: String? = null,
) {
    fun toEpisodeMeta() = EpisodeMeta(
        title = title?.en?.takeIf { it.isNotBlank() } ?: title?.xJat?.takeIf { it.isNotBlank() },
        summary = overview?.takeIf { it.isNotBlank() } ?: summary?.takeIf { it.isNotBlank() },
        image = image?.takeIf { it.isNotBlank() },
    )
}

@Serializable
class AniZipTitleDto(
    val en: String? = null,
    @SerialName("x-jat") val xJat: String? = null,
)

@Serializable
class KitsuEpisodesDto(
    val data: List<KitsuEpisodeDto> = emptyList(),
)

@Serializable
class KitsuEpisodeDto(
    val attributes: KitsuEpisodeAttributesDto,
)

@Serializable
class KitsuEpisodeAttributesDto(
    val number: Int? = null,
    private val canonicalTitle: String? = null,
    private val synopsis: String? = null,
    private val thumbnail: KitsuImageDto? = null,
) {
    fun toEpisodeMeta() = EpisodeMeta(
        title = canonicalTitle?.takeIf { it.isNotBlank() },
        summary = synopsis?.takeIf { it.isNotBlank() },
        image = thumbnail?.original ?: thumbnail?.large,
    )
}

@Serializable
class KitsuImageDto(
    val original: String? = null,
    val large: String? = null,
)
