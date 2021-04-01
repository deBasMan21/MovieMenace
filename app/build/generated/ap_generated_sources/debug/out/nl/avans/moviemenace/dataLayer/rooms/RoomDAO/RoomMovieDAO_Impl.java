package nl.avans.moviemenace.dataLayer.rooms.RoomDAO;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import nl.avans.moviemenace.dataLayer.rooms.Entities.MovieEntity;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RoomMovieDAO_Impl implements RoomMovieDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MovieEntity> __insertionAdapterOfMovieEntity;

  public RoomMovieDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMovieEntity = new EntityInsertionAdapter<MovieEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `MovieEntity` (`movieID`,`title`,`description`,`releaseDate`,`adult`,`status`,`duration`,`popularity`,`url`,`banner`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MovieEntity value) {
        stmt.bindLong(1, value.movieID);
        if (value.title == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.title);
        }
        if (value.description == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.description);
        }
        if (value.releaseDate == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.releaseDate);
        }
        final int _tmp;
        _tmp = value.adult ? 1 : 0;
        stmt.bindLong(5, _tmp);
        if (value.status == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.status);
        }
        stmt.bindLong(7, value.duration);
        stmt.bindLong(8, value.popularity);
        if (value.url == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.url);
        }
        if (value.banner == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.banner);
        }
      }
    };
  }

  @Override
  public void insertMovies(final MovieEntity[] movies) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMovieEntity.insert(movies);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public MovieEntity getMovieByID(final int movieID) {
    final String _sql = "SELECT * FROM MovieEntity WHERE movieID = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, movieID);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMovieID = CursorUtil.getColumnIndexOrThrow(_cursor, "movieID");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
      final int _cursorIndexOfAdult = CursorUtil.getColumnIndexOrThrow(_cursor, "adult");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfPopularity = CursorUtil.getColumnIndexOrThrow(_cursor, "popularity");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfBanner = CursorUtil.getColumnIndexOrThrow(_cursor, "banner");
      final MovieEntity _result;
      if(_cursor.moveToFirst()) {
        final int _tmpMovieID;
        _tmpMovieID = _cursor.getInt(_cursorIndexOfMovieID);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        final String _tmpReleaseDate;
        _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
        final boolean _tmpAdult;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfAdult);
        _tmpAdult = _tmp != 0;
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        final int _tmpPopularity;
        _tmpPopularity = _cursor.getInt(_cursorIndexOfPopularity);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        final String _tmpBanner;
        _tmpBanner = _cursor.getString(_cursorIndexOfBanner);
        _result = new MovieEntity(_tmpMovieID,_tmpTitle,_tmpDescription,_tmpReleaseDate,_tmpAdult,_tmpStatus,_tmpDuration,_tmpPopularity,_tmpUrl,_tmpBanner);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MovieEntity> getAllMovies() {
    final String _sql = "SELECT * FROM MovieEntity ORDER BY popularity DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMovieID = CursorUtil.getColumnIndexOrThrow(_cursor, "movieID");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
      final int _cursorIndexOfAdult = CursorUtil.getColumnIndexOrThrow(_cursor, "adult");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfPopularity = CursorUtil.getColumnIndexOrThrow(_cursor, "popularity");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfBanner = CursorUtil.getColumnIndexOrThrow(_cursor, "banner");
      final List<MovieEntity> _result = new ArrayList<MovieEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MovieEntity _item;
        final int _tmpMovieID;
        _tmpMovieID = _cursor.getInt(_cursorIndexOfMovieID);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        final String _tmpReleaseDate;
        _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
        final boolean _tmpAdult;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfAdult);
        _tmpAdult = _tmp != 0;
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        final int _tmpPopularity;
        _tmpPopularity = _cursor.getInt(_cursorIndexOfPopularity);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        final String _tmpBanner;
        _tmpBanner = _cursor.getString(_cursorIndexOfBanner);
        _item = new MovieEntity(_tmpMovieID,_tmpTitle,_tmpDescription,_tmpReleaseDate,_tmpAdult,_tmpStatus,_tmpDuration,_tmpPopularity,_tmpUrl,_tmpBanner);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MovieEntity> getTop10Movies() {
    final String _sql = "SELECT * FROM MovieEntity ORDER BY Popularity DESC LIMIT 10";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMovieID = CursorUtil.getColumnIndexOrThrow(_cursor, "movieID");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
      final int _cursorIndexOfAdult = CursorUtil.getColumnIndexOrThrow(_cursor, "adult");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfPopularity = CursorUtil.getColumnIndexOrThrow(_cursor, "popularity");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfBanner = CursorUtil.getColumnIndexOrThrow(_cursor, "banner");
      final List<MovieEntity> _result = new ArrayList<MovieEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MovieEntity _item;
        final int _tmpMovieID;
        _tmpMovieID = _cursor.getInt(_cursorIndexOfMovieID);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        final String _tmpReleaseDate;
        _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
        final boolean _tmpAdult;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfAdult);
        _tmpAdult = _tmp != 0;
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        final int _tmpPopularity;
        _tmpPopularity = _cursor.getInt(_cursorIndexOfPopularity);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        final String _tmpBanner;
        _tmpBanner = _cursor.getString(_cursorIndexOfBanner);
        _item = new MovieEntity(_tmpMovieID,_tmpTitle,_tmpDescription,_tmpReleaseDate,_tmpAdult,_tmpStatus,_tmpDuration,_tmpPopularity,_tmpUrl,_tmpBanner);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
