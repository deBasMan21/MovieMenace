package nl.avans.moviemenace.dataLayer.Rooms.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class MovieEntity {
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

    public MovieEntity(int movieID, String title, String description, String releaseDate, boolean adult, String status, int duration, int popularity) {
        this.movieID = movieID;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.adult = adult;
        this.status = status;
        this.duration = duration;
        this.popularity = popularity;
    }
}
