package net.enjoy.springboot.registrationlogin.entity;

import java.util.List;

public class ApiWrapper {
    private List<RoommateRequest> matches;

    public List<RoommateRequest> getMatches() {
        return matches;
    }

    public void setMatches(List<RoommateRequest> matches) {
        this.matches = matches;
    }
}
