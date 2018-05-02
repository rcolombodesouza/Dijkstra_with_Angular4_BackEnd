package com.dijkstra.mail.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class User {

    @Id
    private String id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;


    public User(String firstName, String lastName, String userName, String password){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPassword(password);
        this.setUserName(userName);
    }

    public User(){
        this("", "", "", "");
    }

    @Override
    public String toString() {
        return String.format("User[id='%s', firstName='%s', lastName='%s', userName='%s']", id, firstName, lastName,
                userName);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
