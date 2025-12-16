package edu.upc.dsa.modelos;

public class MemberDTO {
    private String name;   // En lugar de "nombre"
    private String avatar;
    private int points;    // En lugar de "monedas"

    public MemberDTO() {}

    public MemberDTO(String name, String avatar, int points) {
        this.name = name;
        this.avatar = avatar;
        this.points = points;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}