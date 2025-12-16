package com.example.restclientapp.model;
import java.util.List;

public class TeamResponse {
    private String team;
    private List<Member> members; // Usamos la nueva clase Member

    public String getTeam() { return team; }
    public List<Member> getMembers() { return members; }
}