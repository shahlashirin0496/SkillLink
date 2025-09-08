
package com.skilllink.model;

public class Skill {
    private int skillId;
    private String skillName;
    private int skillLevel; // e.g., 1-5

    // Constructor
    public Skill(int skillId, String skillName, int skillLevel) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    // Getters and Setters
    public int getSkillId() { return skillId; }
    public void setSkillId(int skillId) { this.skillId = skillId; }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }

    public int getSkillLevel() { return skillLevel; }
    public void setSkillLevel(int skillLevel) { this.skillLevel = skillLevel; }

    @Override
    public String toString() {
        return skillName + " (Level: " + skillLevel + ")";
    }
}
