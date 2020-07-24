package com.thimu.grapevine.ui;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A room library modelling a book entity
 *
 * @author Kĩthia Ngigĩ
 * @version 23.07.2020
 */
@Entity(tableName = "BOOK_TABLE")
public class Book implements Parcelable {

    // Attributes of the entity
    @PrimaryKey(autoGenerate = true)
    private int identification;
    private String ISBN;
    private int cover;
    private String publisher;
    private String publishDate;
    @NonNull
    private String title;
    private String authors;
    private String genre;
    private String summary;
    private String language;
    private int pages;
    @ColumnInfo(name = "pages_read")
    private int pagesRead;
    private boolean read;

    /**
     * Create a book entity
     * @param ISBN the ISBN of the book
     * @param cover the cover of the book
     * @param publisher the publisher of the book
     * @param publishDate the year the book was published
     * @param title the title of the book
     * @param authors the author(s) of the book
     * @param genre the genre of the book
     * @param summary the summary of the book
     * @param language the language of the book
     * @param pages the number of pages of the book
     * @param pagesRead the number of pages the user has read
     * @param read whether the user has read the book
     */
    public Book(String ISBN, int cover, String publisher, String publishDate, @org.jetbrains.annotations.NotNull String title
            , String authors, String genre, String summary, String language, int pages, int pagesRead, boolean read) {
        this.ISBN = ISBN;
        this.cover = cover;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.title = title;
        this.authors = authors;
        this.genre = genre;
        this.summary = summary;
        this.language = language;
        this.pages = pages;
        this.pagesRead = pagesRead;
        this.read = read; }

    protected Book(Parcel in) {
        identification = in.readInt();
        ISBN = in.readString();
        cover = in.readInt();
        publisher = in.readString();
        publishDate = in.readString();
        title = Objects.requireNonNull(in.readString());
        authors = in.readString();
        genre = in.readString();
        summary = in.readString();
        language = in.readString();
        pages = in.readInt();
        pagesRead = in.readInt();
        read = in.readByte() != 0; }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in); }

        @Override
        public Book[] newArray(int size) {
            return new Book[size]; } };

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
     * Return the cover of the book
     * @return the cover of the book
     */
    public int getCover() {
        return cover; }

    /**
     * Return the publisher of the book
     * @return the publisher of the book
     */
    public String getPublisher() {
        return publisher; }

    /**
     * Return the date the book was published
     * @return the date the book was published
     */
    public String getPublishDate() {
        return publishDate; }

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
     * Return the summary of the book
     * @return the summary of the book
     */
    public String getSummary() {
        return summary; }

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
    public int getPages() {
        return pages; }

    /**
     * Return the number of pages the user has read
     * @return the number of pages the user has read
     */
    public int getPagesRead() {
        return pagesRead; }

    /**
     * Return whether the user has read the book
     * @return whether the user has read the book
     */
    public boolean getRead() {
        return read; }

    @Override
    public int describeContents() {
        return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(identification);
        parcel.writeString(ISBN);
        parcel.writeInt(cover);
        parcel.writeString(publisher);
        parcel.writeString(publishDate);
        parcel.writeString(title);
        parcel.writeString(authors);
        parcel.writeString(genre);
        parcel.writeString(summary);
        parcel.writeString(language);
        parcel.writeInt(pages);
        parcel.writeInt(pagesRead);
        parcel.writeByte((byte) (read ? 1 : 0)); }
}
