package com.thimu.grapevine.ui;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.thimu.grapevine.R;

/**
 * A database to hold the user's book library
 *
 * @author Kithia Ngigĩ
 * @version 16.07.2020
 */
@Database(entities = {Book.class}, version = 2)
public abstract class BookDatabase extends RoomDatabase {

    //
    private static BookDatabase instance;

    public abstract BookDAO bookDAO();

    public static synchronized BookDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BookDatabase.class, "BOOK_DATABASE")
                    .fallbackToDestructiveMigration().addCallback(roomCallback)
                    .build(); }
        return instance; }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulationDbAsyncTask(instance).execute(); } };

    private static class PopulationDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookDAO bookDAO;
        private PopulationDbAsyncTask(BookDatabase db) {
            bookDAO = db.bookDAO(); }

        @Override
        protected Void doInBackground(Void... voids) {
            bookDAO.insert(new Book("9780091906818", R.drawable.ic_kenya_square, "Vermilion", "2006-04-06", "How to Win Friends and Influence People", "Dale Carnegie", "Self-Help", "Millions of people around the world have - and continue to - improve their lives based on the teachings of Dale Carnegie. In \"How to Win Friends and Influence People\", Carnegie offers practical advice and techniques, in his exuberant and conversational style, for how to get out of a mental rut and make life more rewarding. His advice has stood the test of time and will teach you how to: make friends quickly and easily; increase your popularity; win people to your way of thinking; enable you to win new clients and customers; become a better speaker and a more entertaining conversationalist; and, arouse enthusiasm among your colleagues. This book will turn around your relationships and improve your dealings with all the people in your life.", "English", 292, 292, true));
            bookDAO.insert(new Book("9781508435303", R.drawable.ic_kenya_square, "CreateSpace Independent Publishing Platform", "2013-12-23", "Stephen Curry: The Inspirational Story of Basketball Superstar Stephen Curry", "Bill Redban", "Biography", "Discover The Inspirational Story of Basketball Superstar Stephen Curry! Read on your PC, Mac, smart phone, tablet or Kindle device! You're about to discover the incredibly inspirational story of basketball superstar Stephen Curry. If you're reading this then you must be a Stephen Curry fan, like so many others. As a fan, you must wonder how this man is so talented and want to know more about him. Stephen Curry is considered as one of the greatest basketball players in the world and it's been an honor to be able to watch him play throughout his career. This book will reveal to you much about Stephen Curry's story and the many accomplishments throughout his career. Here Is A Preview Of What You'll Learn...\n\nYouth and Family Life\nHigh School and College Career\nProfessional Career and Personal Life\nLegacy, Charitable Acts and much more!", "English", 60, 60, true));

            bookDAO.insert(new Book("9781451639612", R.drawable.ic_kenya_square, "Simon & Schuster", "2013-01-01", "The 7 Habits of Highly Effective People: Powerful Lessons in Personal Change", "Stephen R. Covey", "Self-Help", "One of the most inspiring and impactful books ever written, The 7 Habits of Highly Effective People has captivated readers for 25 years. It has transformed the lives of presidents and CEOs, educators and parents--in short, millions of people of all ages and occupations across the world. This twenty-fifth anniversary edition of Stephen Covey's cherished classic commemorates his timeless wisdom, and encourages us to live a life of great and enduring purpose.", "English", 391, 158, false));
            bookDAO.insert(new Book("9781472139955", R.drawable.ic_kenya_square, "Little, Brown", "2017-01-12", "Mindset: Changing The Way You think To Fulfil Your Potential", "Dr. Carol S Dweck", "Self-Help", "An authoritative, practical guide on how to develop the mindset necessary for success, both personal and professional. - Revised and updated with new material.", "English", 341, 44, false));
            bookDAO.insert(new Book("9780805080018", R.drawable.ic_kenya_square, "Henry Holt", "2005-12-27", "Imperial Reckoning: The Untold Story of Britain's Gulag in Kenya", "Caroline Elkins", "History", "A major work of history that for the first time reveals the violence and terror at the heart of Britain's civilizing mission in Kenya", "English", 475, 15, false));
            bookDAO.insert(new Book("9781780173641", R.drawable.ic_kenya_square, "BCS Learning & Development Limited", "2017-08-31", "Computational Thinking: A Beginner's Guide to Problem-Solving and Programming", "Karl Beecher", "Textbook", "Computational thinking is a timeless, transferable skill that enables you to think more clearly and logically, as well as a way to solve specific problems. Beginning with the core ideas of computational thinking, with this book you'll build up an understanding of the practical problem-solving approach and explore how computational thinking aids good practice in programming, complete with a full guided example.", "English", 306, 0, false));
            bookDAO.insert(new Book("9781406372151", R.drawable.ic_kenya_square, "Walker Books", "2017-04-06", "The Hate U Give", "Angie Thomas", "Crime Fiction", "Sixteen-year-old Starr lives in two worlds: the poor neighbourhood where she was born and raised and her posh high school in the suburbs. The uneasy balance between them is shattered when Starr is the only witness to the fatal shooting of her unarmed best friend, Khalil, by a police officer. Now what Starr says could destroy her community. It could also get her killed.\n\nInspired by the Black Lives Matter movement, this is a powerful and gripping YA novel about one girl's struggle for justice.", "English", 437, 0, false));
            bookDAO.insert(new Book("9780007259762", R.drawable.ic_kenya_square, "HarperCollins Publishers", "2011-05-03", "Holy Bible: King James Version", "Various Authors", "Religion", "Written in 1611, the KJV is regarded as one of the most accurate translations of the Bible into the English language. The translation is often requested for ceremonial events and given to mark special occasions.", "English", 1152, 0, false));
            return null; } }
}
