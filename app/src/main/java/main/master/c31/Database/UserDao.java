package main.master.c31.Database;

import androidx.room.*;

import java.util.List;

@Dao
public interface  UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insert(User user);

    @Query("DELETE FROM User")
    void delete();

    @Update
    void update(User user);

    @Query("UPDATE user SET owner_name =:owner_name ,ps_email=:ps_email , center_address =:center_address ,ps_mobile=:ps_mobile, website =:website ,facebook_link=:facebook_link WHERE preschool_id =:preschool_id")
    int updateUser(String preschool_id, String owner_name, String ps_email, String center_address, String ps_mobile, String website, String facebook_link);
}
