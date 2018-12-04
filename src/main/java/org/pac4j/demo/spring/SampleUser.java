package org.pac4j.demo.spring;


public class SampleUser {
    private String name;
    private String email;

    public SampleUser() {
        this.name = null;
        this.email = null;
    }

    public SampleUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return this.email; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(Name: ");
        sb.append(this.name);
        sb.append(", Email: ");
        sb.append(this.email);
        sb.append(")");
        return sb.toString();
    }

}
