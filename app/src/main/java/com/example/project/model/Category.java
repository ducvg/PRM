package com.example.project.model;

import java.util.HashSet;
import java.util.Set;

public class Category {
    private int categoryId;
    private String name;
    private Set<Task> tasks = new HashSet<>();

}
