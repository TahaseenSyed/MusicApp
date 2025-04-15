package com.klu.musicplayer

import android.os.Parcel
import android.os.Parcelable

// Define the Song data class that implements Parcelable
data class Song(
    val title: String,
    val artist: String,    // Ensure artist is included
    val imageResId: Int,
    val audioResId: Int
) : Parcelable {

    // Constructor that reads from a Parcel to re-create the object
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",      // Read title
        parcel.readString() ?: "",      // Read artist
        parcel.readInt(),               // Read imageResId
        parcel.readInt()                // Read audioResId
    )

    // Function to write the object's data to the Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)       // Write title
        parcel.writeString(artist)      // Write artist
        parcel.writeInt(imageResId)     // Write imageResId
        parcel.writeInt(audioResId)     // Write audioResId
    }

    // Return 0 because we aren't dealing with any special content (like files)
    override fun describeContents(): Int = 0

    // Companion object that handles creating a Song instance from a Parcel
    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song = Song(parcel)  // Create Song from Parcel
        override fun newArray(size: Int): Array<Song?> = arrayOfNulls(size) // Create an array of Songs
    }
}
