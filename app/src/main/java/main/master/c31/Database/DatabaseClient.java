package main.master.c31.Database;


import android.content.Context;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private AppDatabase appDatabase;


    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //CreativeConnectDB is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "CreativeConnectDB")
                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries().build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'user' ADD COLUMN 'ps_social_media_manager' TEXT");
            database.execSQL("ALTER TABLE 'user' ADD COLUMN 'ps_graphics_designer' TEXT");
            database.execSQL("ALTER TABLE 'user' ADD COLUMN 'ps_content_writer' TEXT");

            database.execSQL("ALTER TABLE 'ActivityTable' ADD COLUMN 'activity_title' TEXT");

        }
    };
}
