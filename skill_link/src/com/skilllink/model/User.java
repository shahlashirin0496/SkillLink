
package com.skilllink.model;

import java.util.List;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private List<Skill> skills; // User's skills

    // Constructor
    public User(int userId, String name, String email, String password, List<Skill> skills) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.skills = skills;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Skill> getSkills() { return skills; }
    public void setSkills(List<Skill> skills) { this.skills = skills; }

    // Utility method
    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    public void removeSkill(Skill skill) {
        this.skills.remove(skill);
    }
}
