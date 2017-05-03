package com.team.answer.models;

/**
 * Created by abdelmageed on 02/05/17.
 */

public class Teams {

    private String teamId;
    private String macAddress;
    private String state;


    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
