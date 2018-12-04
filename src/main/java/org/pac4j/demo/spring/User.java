package org.pac4j.demo.spring;


public class User {
    private String name;
    private String email;
    private String userid;
    private String password;

    public User() {
        this.name = null;
        this.email = null;
        this.userid = null;
        this.password = null;
    }

    public User(String name, String email, String userid, String password) {
        this.name = name;
        this.email = email;
        this.userid = userid;
        this.password = password;
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return this.email; }

    public void setUserid(String userid) { this.userid = userid; }
    public String getUserid() { return this.userid; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return this.password; }

    public boolean hasUPfield() {
        return (!this.userid.equals("") && !this.password.equals(""));
    }

    public boolean hasAllField() {
        return (this.name.compareTo("") != 0
                && this.email.compareTo("") != 0
                && this.userid.compareTo("") != 0
                && this.password.compareTo("") != 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(name: ");
        sb.append(this.name);
        sb.append(", email: ");
        sb.append(this.email);
        sb.append(", userid: ");
        sb.append(this.userid);
        sb.append(", password: ");
        sb.append(this.password);
        sb.append(")");
        return sb.toString();
    }

}
