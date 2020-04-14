package main.master.c31.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import main.master.c31.Room.ActivityTable;
import main.master.c31.Room.ActivityTableDao;
@Database(entities = {User.class, ActivityTable.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ActivityTableDao activityTableDao();

}
