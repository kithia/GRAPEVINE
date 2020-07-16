package com.thimu.grapevine.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * The book view model
 *
 * @author Kithia Ngigĩ
 * @version 04.07.2020
 */
public class BookViewModel extends AndroidViewModel {

    //
    private BookRepository repository;
    private LiveData<List<Book>> allBooks;

    /**
     * Create the view model
     * @param application
     */
    public BookViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
        allBooks = repository.getAllBooks(); }

    public void insert(Book book) {
        repository.insert(book); }

    public void update(Book book) {
        repository.update(book); }

    public void remove(Book book) {
        repository.remove(book); }

    public void removeAllBooks() {
        repository.removeAllBooks(); }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks; }
}
