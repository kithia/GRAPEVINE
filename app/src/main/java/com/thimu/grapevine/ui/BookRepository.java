package com.thimu.grapevine.ui;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * The book repository
 *
 * @author Kithia Ngigĩ
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
        allBooks = bookDAO.getAllBooksIdentificationDescending(); }

    public void insert(Book book) {
        new InsertBookAsyncTask(bookDAO).execute(book); }

    public void update(Book book) {
        new UpdateBookAsyncTask(bookDAO).execute(book); }

    public void remove(Book book) {
        new RemoveBookAsyncTask(bookDAO).execute(book); }

    public void removeAllBooks() {
        new RemoveAllBooksAsyncTask(bookDAO).execute(); }

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

    private static class RemoveBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDAO bookDAO;

        private RemoveBookAsyncTask(BookDAO bookDAO) {
            this.bookDAO = bookDAO; }

        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.remove(books[0]);
            return null; } }

    private static class RemoveAllBooksAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookDAO bookDAO;

        private RemoveAllBooksAsyncTask(BookDAO bookDAO) {
            this.bookDAO = bookDAO; }

        @Override
        protected Void doInBackground(Void... voids) {
            bookDAO.removeAllBooks();
            return null; } }
}
