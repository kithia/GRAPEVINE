package com.thimu.grapevine.ui;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * An interface to define interactions with the database
 *
 * @author Obed Ngigi
 * @version 03.07.2020
 */
@Dao
public interface BookDAO {

    // Data-manipulation operations of the database
    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("DELETE FROM BOOK_TABLE")
    void deleteALLBooks();

    @Query("SELECT * FROM BOOK_TABLE ORDER BY identification DESC")
    LiveData<List<Book>> getAllBooks();
}
