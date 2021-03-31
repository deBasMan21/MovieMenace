package nl.avans.moviemenace.dataLayer.Rooms.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Movie {
    @PrimaryKey
    public int movieID;

    @ColumnInfo
    @NonNull
    public String title;

    @ColumnInfo
    @NonNull
    public String description;

    @ColumnInfo
    @NonNull
    public LocalDate releaseDate;

    @ColumnInfo
    @NonNull
    public boolean adult;

    @ColumnInfo
    @NonNull
    public String status;

    @ColumnInfo
    @NonNull
    public int duration;

    @ColumnInfo
    @NonNull
    public int popularity;
}
