package com.thimu.grapevine.ui;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

/**
 * A room library modelling a book entity
 *
 * @author Obed Ngigi
 * @version 04.07.2020
 */
@Entity(tableName = "BOOK_TABLE")
public class Book {

    // Attributes of the entity
    @PrimaryKey(autoGenerate = true)
    private int identification;
    private String ISBN;
    private String publisher;
    private String publishedYear;
    @NonNull
    private String title;
    private String authors;
    private String genre;
    private String description;
    private String language;
    private String pages;

    /**
     * Create a book entity
     * @param ISBN the ISBN of the book
     * @param publisher the publisher of the book
     * @param publishedYear the year the book was published
     * @param title the title of the book
     * @param authors the author(s) of the book
     * @param genre the genre of the book
     * @param description the description of the book
     * @param language the language of the book
     * @param pages the number of pages of the book
     */
    public Book(String ISBN, String publisher, String publishedYear, @org.jetbrains.annotations.NotNull String title, String authors, String genre, String description, String language, String pages) {
        this.ISBN = ISBN;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.title = title;
        this.authors = authors;
        this.genre = genre;
        this.description = description;
        this.language = language;
        this.pages = pages; }

    /**
     * Set the ID of the book
     * @param identification the ID of the book
     */
    public void setIdentification(int identification) {
        this.identification = identification; }

    /**
     * Return the ID of the book
     * @return the ID of the book
     */
    public int getIdentification() {
        return identification; }

    /**
     * Return the ISBN of the book
     * @return the ISBN of the book
     */
    public String getISBN() {
        return ISBN; }

    /**
     * Return the publisher of the book
     * @return the publisher of the book
     */
    public String getPublisher() {
        return publisher; }

    /**
     * Return the year the book was published
     * @return the year the book was published
     */
    public String getPublishedYear() {
        return publishedYear; }

    /**
     * Return the title of the book
     * @return the title of the book
     */
    @NotNull
    public String getTitle() {
        return title; }

    /**
     * Return the author(s) of the book
     * @return the author(s) of the book
     */
    public String getAuthors() {
        return authors; }

    /**
     * Return the genre of the book
     * @return the genre of the book
     */
    public String getGenre() {
        return genre; }

    /**
     * Return the description of the book
     * @return the description of the book
     */
    public String getDescription() {
        return description; }

    /**
     * Return the language of the book
     * @return the language of the book
     */
    public String getLanguage() {
        return language; }

    /**
     * Return the number of pages of the book
     * @return the number of pages of the book
     */
    public String getPages() {
        return pages; }
}
