package com.klu.musicplayer

import android.os.Parcel
import android.os.Parcelable

data class Playlist(
    val name: String,
    val imageResId: Int,
    val description: String,
    val songs: List<Song>
) : Parcelable {
    // Constructor to read from Parcel
    private constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        mutableListOf<Song>().apply {
            parcel.readList(this, Song::class.java.classLoader)
        }
    )

    // Write data to Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(imageResId)
        parcel.writeString(description)  // Correctly writing the description as a String
        parcel.writeList(songs)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Playlist> {
        override fun createFromParcel(parcel: Parcel): Playlist {
            return Playlist(parcel)
        }

        override fun newArray(size: Int): Array<Playlist?> {
            return arrayOfNulls(size)
        }
    }
}
