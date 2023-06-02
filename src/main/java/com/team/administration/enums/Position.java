package com.team.administration.enums;

public enum Position {
    CEO("ceo"),
    CTO("cto"),
    MANAGER("Manager"),
    LEAD("Lead"),
    DEVELOPER("Developer"),
    UI_DEVELOPER("UI Developer"),
    FRONTEND_DEVELOPER("Frontend Developer");

    Position(String name){
        this.name = name;
    }
    private final String name;
    public String toString(){
        return name;
    }
}
