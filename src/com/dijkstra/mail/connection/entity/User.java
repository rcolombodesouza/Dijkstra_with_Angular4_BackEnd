package com.dijkstra.mail.connection.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by rafael.colombo on 06/06/2017.
 */
@Document
public class User {

    @Id
    private String id;

    private String userName;
    private String password;
    private String firstName;
    private String lastName;


    public User(String _firstName, String _lastName, String _userName, String _password){
        this.setFirstName(_firstName);
        this.setLastName(_lastName);
        this.setPassword(_password);
        this.setUserName(_userName);
    }

    public User(){
        this("", "", "", "");
    }

    @Override
    public String toString() {
        return String.format("User[id='%s', firstName='%s', lastName='%s', userName='%s']", id, firstName, lastName, userName);
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
