package com.dao.mymovies.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.databinding.ObservableField
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created in 27/07/18 10:22.
 *
 * @author Diogo Oliveira.
 */
@Parcelize
@Entity(tableName = "Movies")
data class Movie(
        @Expose
        @NonNull
        @PrimaryKey
        @SerializedName("id")
        var id: Int,
        @Expose
        @NonNull
        @SerializedName("title")
        var title: String,
        @Expose
        @NonNull
        @SerializedName("release_date")
        var releaseDate: Date?,
        @Expose
        @NonNull
        @SerializedName("vote_average")
        var voteAverage: Float,
        @Expose
        @NonNull
        @SerializedName("overview")
        var overView: String,
        @Expose
        @SerializedName("poster_path")
        var cover: String,

        @Ignore
        @Expose(serialize = false, deserialize = false)
        var isFavorite: ObservableField<Boolean> = ObservableField(false)) : Parcelable
{
        constructor(): this(0, "", null, 0F, "", "", ObservableField(false))

        override fun toString(): String
        {
            return title
        }
}
