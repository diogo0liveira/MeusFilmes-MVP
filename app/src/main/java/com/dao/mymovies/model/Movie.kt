package com.dao.mymovies.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.databinding.ObservableField
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created in 27/07/18 10:22.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
@Entity(tableName = "Movies")
@JsonClass(generateAdapter = true)
data class Movie(
        @NonNull
        @PrimaryKey
        @Json(name = "id")
        var id: Int,
        @NonNull
        @Json(name = "title")
        var title: String,
        @NonNull
        @Json(name = "release_date")
        var releaseDate: Date?,
        @NonNull
        @Json(name = "vote_average")
        var voteAverage: Float,
        @NonNull
        @Json(name = "overview")
        var overView: String,
        @Json(name = "poster_path")
        var cover: String?,

        @Ignore
        @Transient
        var isFavorite: ObservableField<Boolean> = ObservableField(false)) : Parcelable
{
        constructor(): this(0, "", null, 0F, "", null, ObservableField(false))

        override fun toString(): String
        {
            return title
        }
}
