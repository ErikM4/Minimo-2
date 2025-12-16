package edu.upc.dsa.modelos;
import java.util.List;

public class TeamResponse {
    private String team;
    private List<MemberDTO> members; // Cambiado a MemberDTO

    public TeamResponse() {}
    public TeamResponse(String team, List<MemberDTO> members) {
        this.team = team;
        this.members = members;
    }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }
    public List<MemberDTO> getMembers() { return members; }
    public void setMembers(List<MemberDTO> members) { this.members = members; }
}