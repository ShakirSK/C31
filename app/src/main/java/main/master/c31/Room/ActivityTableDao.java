package main.master.c31.Room;

import androidx.room.*;

import java.util.List;

@Dao
public interface ActivityTableDao {


    @Query("SELECT * FROM ActivityTable")
    List<ActivityTable> getAll();

    @Query("SELECT * FROM ActivityTable ORDER BY id LIMIT 1")
    List<ActivityTable> getcheck();


    @Insert
    void insert(ActivityTable activityTable);

    @Query("DELETE FROM ActivityTable WHERE  activity_name = :activityname")
    void delete(String activityname);

    @Update
    void update(ActivityTable activityTable);


}
