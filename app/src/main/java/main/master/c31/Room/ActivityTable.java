package main.master.c31.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ActivityTable implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "activity_name")
    private String activityname;


    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }

    public String getActivitydate() {
        return activitydate;
    }

    public void setActivitydate(String activitydate) {
        this.activitydate = activitydate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ColumnInfo(name = "activity_date")
    private String activitydate;

    @ColumnInfo(name = "description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }







}
