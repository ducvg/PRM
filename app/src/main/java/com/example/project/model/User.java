package com.example.project.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private Date createdAt;
    private int role;
    private Set<Task> tasks = new HashSet<>();

}
