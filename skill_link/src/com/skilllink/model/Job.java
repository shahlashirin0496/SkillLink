package com.skilllink.model;

import java.util.List;

public class Job {
    private int jobId;
    private String title;
    private String description;
    private List<Skill> requiredSkills;

    // Constructor
    public Job(int jobId, String title, String description, List<Skill> requiredSkills) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.requiredSkills = requiredSkills;
    }

    // Getters and Setters
    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Skill> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<Skill> requiredSkills) { this.requiredSkills = requiredSkills; }

    // Utility method
    public void addRequiredSkill(Skill skill) {
        this.requiredSkills.add(skill);
    }

    public void removeRequiredSkill(Skill skill) {
        this.requiredSkills.remove(skill);
    }
}
