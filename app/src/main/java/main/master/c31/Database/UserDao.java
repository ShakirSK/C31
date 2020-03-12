package main.master.c31.Database;

import androidx.room.*;

import java.util.List;

@Dao
public interface  UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);
}
