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
 * @author Kĩthia Ngigĩ
 * @version 05.08.2020
 */
@Database(entities = {Book.class}, version = 2)
public abstract class BookDatabase extends RoomDatabase {

    //
    private static BookDatabase instance;

    public abstract BookDAO bookDAO();

    // Synchronized keyword prevents multiple threads from accessing this method at once;
    // only one database is ever created
    // .fallbackToDestructiveMigration() method prevents an illegalSateException when updating the version number;
    // the schema is deleted and rebuilt at version incrementation
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
            bookDAO.insert(new Book("9780091906818", R.drawable.vietnam_square, "Vermilion", "2006-04-06", "How to Win Friends and Influence People", "Dale Carnegie", "Self-Help", "The most famous confidence-boosting book ever published; with sales of over 16 million copies worldwide\n" +
                    "\n" +
                    "Millions of people around the world have improved their lives based on the teachings of Dale Carnegie. In How to Win Friends and Influence People, he offers practical advice and techniques, in his exuberant and conversational style, for how to get out of a mental rut and make life more rewarding.\n" +
                    "\n" +
                    "His advice has stood the test of time and will teach you how to:\n" +
                    "- make friends quickly and easily\n" +
                    "- increase your popularity\n" +
                    "- persuade people to follow your way of thinking\n" +
                    "- enable you to win new clients and customers\n" +
                    "- become a better speaker\n" +
                    "- boost enthusiasm among your colleagues\n" +
                    "\n" +
                    "This classic book will turn your relationships around and improve your interactions with everyone in your life.", "English", R.string.paperback, 292, 292, true));

            bookDAO.insert(new Book("9781508435303", R.drawable.jamaica_square, "CreateSpace Independent Publishing Platform", "2013-12-23", "Stephen Curry: The Inspirational Story of Basketball Superstar Stephen Curry", "Bill Redban", "Biography", "Discover The Inspirational Story of Basketball Superstar Stephen Curry! Read on your PC, Mac, smart phone, tablet or Kindle device! You're about to discover the incredibly inspirational story of basketball superstar Stephen Curry. If you're reading this then you must be a Stephen Curry fan, like so many others. As a fan, you must wonder how this man is so talented and want to know more about him. Stephen Curry is considered as one of the greatest basketball players in the world and it's been an honor to be able to watch him play throughout his career. This book will reveal to you much about Stephen Curry's story and the many accomplishments throughout his career. Here Is A Preview Of What You'll Learn...\n" +
                    "\n" +
                    "Youth and Family Life\n" +
                    "High School and College Career\n" +
                    "Professional Career and Personal Life\n" +
                    "Legacy, Charitable Acts and much more!", "English", R.string.paperback, 60, 60, true));

            bookDAO.insert(new Book("9781451639612", R.drawable.taiwan_square, "Simon & Schuster", "2013-01-01", "The 7 Habits of Highly Effective People: Powerful Lessons in Personal Change", "Stephen R. Covey", "Self-Help", "One of the most inspiring and impactful books ever written, The 7 Habits of Highly Effective People has captivated readers for 25 years. It has transformed the lives of presidents and CEOs, educators and parents--in short, millions of people of all ages and occupations across the world. This twenty-fifth anniversary edition of Stephen Covey's cherished classic commemorates his timeless wisdom, and encourages us to live a life of great and enduring purpose.", "English", R.string.paperback, 391, 158, false));

            bookDAO.insert(new Book("9781472139955", R.drawable.malaysia_square, "Little, Brown", "2017-01-12", "Mindset: Changing The Way You think To Fulfil Your Potential", "Dr. Carol S Dweck", "Self-Help", "World-renowned Stanford University psychologist Carol Dweck, in decades of research on achievement and success, has discovered a truly groundbreaking idea-the power of our mindset.\n" +
                    "\n" +
                    "Dweck explains why it's not just our abilities and talent that bring us success-but whether we approach them with a fixed or growth mindset. She makes clear why praising intelligence and ability doesn't foster self-esteem and lead to accomplishment, but may actually jeopardize success. With the right mindset, we can motivate our kids and help them to raise their grades, as well as reach our own goals-personal and professional. Dweck reveals what all great parents, teachers, CEOs, and athletes already know: how a simple idea about the brain can create a love of learning and a resilience that is the basis of great accomplishment in every area.", "English", R.string.paperback, 341, 44, false));

            bookDAO.insert(new Book("9780805080018", R.drawable.kenya_square, "Henry Holt", "2005-12-27", "Imperial Reckoning: The Untold Story of Britain's Gulag in Kenya", "Caroline Elkins", "History", "A major work of history that for the first time reveals the violence and terror at the heart of Britain's civilizing mission in Kenya\n" +
                    "\n" +
                    "As part of the Allied forces, thousands of Kenyans fought alongside the British in World War II. But just a few years after the defeat of Hitler, the British colonial government detained nearly the entire population of Kenya's largest ethnic minority, the Kikuyu-some one and a half million people.\n" +
                    "\n" +
                    "The compelling story of the system of prisons and work camps where thousands met their deaths has remained largely untold-the victim of a determined effort by the British to destroy all official records of their attempts to stop the Mau Mau uprising, the Kikuyu people's ultimately successful bid for Kenyan independence.\n" +
                    "\n" +
                    "Caroline Elkins, an assistant professor of history at Harvard University, spent a decade in London, Nairobi, and the Kenyan countryside interviewing hundreds of Kikuyu men and women who survived the British camps, as well as the British and African loyalists who detained them.\n" +
                    "\n" +
                    "The result is an unforgettable account of the unraveling of the British colonial empire in Kenya-a pivotal moment in twentieth- century history with chilling parallels to America's own imperial project.\n" +
                    "\n" +
                    "Imperial Reckoning is the winner of the 2006 Pulitzer Prize for Nonfiction.", "English", R.string.paperback, 475, 15, false));

            bookDAO.insert(new Book("9781780173641", R.drawable.zimbabwe_square, "BCS Learning & Development Limited", "2017-08-31", "Computational Thinking: A Beginner's Guide to Problem-Solving and Programming", "Karl Beecher", "Textbook", "Computational thinking (CT) is a timeless, transferable skill that enables you to think more clearly and logically, as well as a way to solve specific problems. With this book you'll learn to apply computational thinking in the context of software development to give you a head start on the road to becoming an experienced and effective programmer. Beginning with the core ideas of computational thinking, with this book you'll build up an understanding of the practical problem-solving approach and explore how computational thinking aids good practice in programming, complete with a full guided example.", "English", R.string.paperback, 306, 0, false));

            bookDAO.insert(new Book("9780007259762", R.drawable.israel_square, "HarperCollins Publishers", "2011-05-03", "Holy Bible: King James Version", "Elohim-Breathed", "Religion", "A handsome leather edition of the bestselling King James Version translation. This is a timeless classic and a wonderful gift, prize or keepsake.\n" +
                    "\n" +
                    "The King James Version has been a popular bestseller for centuries, with Collins selling over 1 million gift Bibles alone.\n" +
                    "\n" +
                    "This edition comes with a quality leather binding, sturdy slipcase, gilt pages, ribbon marker and presentation page, making it ideal as a gift or prize. With its durable binding and classic presentation, it is also suitable for personal use.\n" +
                    "\n" +
                    "This full text edition of the ever-popular Authorized King James Version Bible includes both the Old and New Testaments, with all its literary beauty and poetic grandeur.\n" +
                    "\n" +
                    "Written in 1611, the KJV is regarded as one of the most accurate translations of the Bible into the English language. The translation is often requested for ceremonial events and given to mark special occasions.", "English", R.string.leather_bound, 1152, 0, false));

            return null; } }
}
