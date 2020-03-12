package main.master.c31.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "preschool_id")
    private String preschool_id;

    @ColumnInfo(name = "ps_user_id")
    private String ps_user_id;

    @ColumnInfo(name = "ps_name")
    private String ps_name;

    public String getPs_activities() {
        return ps_activities;
    }

    public void setPs_activities(String ps_activities) {
        this.ps_activities = ps_activities;
    }

    @ColumnInfo(name = "ps_activities")
    private String ps_activities;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreschool_id() {
        return preschool_id;
    }

    public void setPreschool_id(String preschool_id) {
        this.preschool_id = preschool_id;
    }

    public String getPs_user_id() {
        return ps_user_id;
    }

    public void setPs_user_id(String ps_user_id) {
        this.ps_user_id = ps_user_id;
    }

    public String getPs_name() {
        return ps_name;
    }

    public void setPs_name(String ps_name) {
        this.ps_name = ps_name;
    }

    public boolean isPs_email() {
        return ps_email;
    }

    public void setPs_email(boolean ps_email) {
        this.ps_email = ps_email;
    }

    @ColumnInfo(name = "ps_email")
    private boolean ps_email;


}
