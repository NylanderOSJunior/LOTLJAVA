package model;

public class Sessao {
    private int sid;
    private int serial;
    private String username;
    private String status;
    private String machine;

    public Sessao(int sid, int serial, String username, String status, String machine) {
        this.sid = sid;
        this.serial = serial;
        this.username = username != null ? username : "Desconhecido"; // Tratamento de valor nulo
        this.status = status != null ? status : "Desconhecido"; // Tratamento de valor nulo
        this.machine = machine != null ? machine : "Desconhecida"; // Tratamento de valor nulo
    }

    public int getSid() { return sid; }
    public int getSerial() { return serial; }
    public String getUsername() { return username; }
    public String getStatus() { return status; }
    public String getMachine() { return machine; }
}

