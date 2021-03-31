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
    public String title;

    @ColumnInfo
    public String description;

    @ColumnInfo
    public String releaseDate;

    @ColumnInfo
    public boolean adult;

    @ColumnInfo
    public String status;

    @ColumnInfo
    public int duration;

    @ColumnInfo
    public int popularity;
}
