package com.thimu.grapevine.ui;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * The book repository
 *
 * @author Obed Ngigi
 * @version 03.07.2020
 */
public class BookRepository {

    //
    private BookDAO bookDAO;
    private LiveData<List<Book>> allBooks;

    /**
     * Create the repository
     * @param application
     */
    public BookRepository(Application application) {
        BookDatabase database = BookDatabase.getInstance(application);
        bookDAO = database.bookDAO();
        allBooks = bookDAO.getAllBooks(); }

    public void insert(Book book) {
        new InsertBookAsyncTask(bookDAO).execute(book); }

    public void update(Book book) {
        new UpdateBookAsyncTask(bookDAO).execute(book); }

    public void delete(Book book) {
        new DeleteBookAsyncTask(bookDAO).execute(book); }

    public void deleteAllNotes() {
        new DeleteAllBooksAsyncTask(bookDAO).execute(); }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks; }

    private static class InsertBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDAO bookDAO;

        private InsertBookAsyncTask(BookDAO bookDAO) {
            this.bookDAO = bookDAO; }

        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.insert(books[0]);
            return null; } }

    private static class UpdateBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDAO bookDAO;

        private UpdateBookAsyncTask(BookDAO bookDAO) {
            this.bookDAO = bookDAO; }

        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.update(books[0]);
            return null; } }

    private static class DeleteBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDAO bookDAO;

        private DeleteBookAsyncTask(BookDAO bookDAO) {
            this.bookDAO = bookDAO; }

        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.delete(books[0]);
            return null; } }

    private static class DeleteAllBooksAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookDAO bookDAO;

        private DeleteAllBooksAsyncTask(BookDAO bookDAO) {
            this.bookDAO = bookDAO; }

        @Override
        protected Void doInBackground(Void... voids) {
            bookDAO.deleteALLBooks();
            return null; } }
}
