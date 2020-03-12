package main.master.c31.Network;

public class loginmodel {
    public String getPs_user_id() {
        return ps_user_id;
    }

    public void setPs_user_id(String ps_user_id) {
        this.ps_user_id = ps_user_id;
    }

    public String getPs_password() {
        return ps_password;
    }

    public void setPs_password(String ps_password) {
        this.ps_password = ps_password;
    }

    public loginmodel(String ps_user_id, String ps_password) {
        this.ps_user_id = ps_user_id;
        this.ps_password = ps_password;
    }

    private String ps_user_id;

    private String ps_password;
}
