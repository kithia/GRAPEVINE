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
 * @version 09.07.2020
 */
@Dao
public interface BookDAO {

    // Data-manipulation operations of the database
    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void remove(Book book);

    @Query("DELETE FROM BOOK_TABLE")
    void removeAllBooks();

    @Query("SELECT * FROM BOOK_TABLE ORDER BY identification DESC")
    LiveData<List<Book>> getAllBooksIdentificationDescending();

    @Query("SELECT * FROM BOOK_TABLE ORDER BY identification ASC")
    LiveData<List<Book>> getAllBooksIdentificationAscending();

    @Query("SELECT * FROM BOOK_TABLE ORDER BY title DESC")
    LiveData<List<Book>> getAllBooksTitleDescending();

    @Query("SELECT * FROM BOOK_TABLE ORDER BY title ASC")
    LiveData<List<Book>> getAllBooksTitleAscending();

    @Query("SELECT * FROM BOOK_TABLE ORDER BY authors DESC")
    LiveData<List<Book>> getAllBooksAuthorsDescending();

    @Query("SELECT * FROM BOOK_TABLE ORDER BY authors ASC")
    LiveData<List<Book>> getAllBooksAuthorsAscending();

    @Query("SELECT * FROM BOOK_TABLE ORDER BY publisher DESC")
    LiveData<List<Book>> getAllBooksPublisherDescending();

    @Query("SELECT * FROM BOOK_TABLE ORDER BY publisher ASC")
    LiveData<List<Book>> getAllBooksPublisherAscending();
}
