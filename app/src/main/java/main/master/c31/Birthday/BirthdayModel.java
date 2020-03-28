package main.master.c31.Birthday;

public class BirthdayModel {


    public String getPreschool_id() {
        return preschool_id;
    }

    public void setPreschool_id(String preschool_id) {
        this.preschool_id = preschool_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    private String preschool_id;

    public BirthdayModel(String preschool_id, String student_name, String dob, String file, String created_by) {
        this.preschool_id = preschool_id;
        this.student_name = student_name;
        this.dob = dob;
        this.file = file;
        this.created_by = created_by;
    }

    private String student_name;
    private String dob;
    private String file;
    private String created_by;


}
