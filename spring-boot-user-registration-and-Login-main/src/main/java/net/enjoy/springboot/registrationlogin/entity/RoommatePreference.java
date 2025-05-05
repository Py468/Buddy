package net.enjoy.springboot.registrationlogin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoommatePreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String accommodationType;
    private String preferredArea;
    private String gender;
    private String personality;
    private String occupation;
    private String sleepSchedule;
    private Integer cleanlinessPreference;
    private Integer budgetRange;
    private String interests;
    private String socialActivityLevel;

    public String getAccommodationType() {

        return accommodationType;
    }

    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }

    public String getPreferredArea() {
        return preferredArea;
    }

    public void setPreferredArea(String preferredArea) {
        this.preferredArea = preferredArea;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSleepSchedule() {
        return sleepSchedule;
    }

    public void setSleepSchedule(String sleepSchedule) {
        this.sleepSchedule = sleepSchedule;
    }

    public Integer getCleanlinessPreference() {
        return cleanlinessPreference;
    }

    public void setCleanlinessPreference(Integer cleanlinessPreference) {
        this.cleanlinessPreference = cleanlinessPreference;
    }

    public Integer getBudgetRange() {
        return budgetRange;
    }

    public void setBudgetRange(Integer budgetRange) {
        this.budgetRange = budgetRange;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getSocialActivityLevel() {
        return socialActivityLevel;
    }

    public void setSocialActivityLevel(String socialActivityLevel) {
        this.socialActivityLevel = socialActivityLevel;
    }
}
