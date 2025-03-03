package com.example.model;

public class Sessao {
    private int sid;
    private String sql_id;
    private String username;
    private String osuser;
    private String program;
    private String status;
    private String machine;
    private int seconds_in_wait;
    private String sql_text;
    private String blocking_session;  
    private String event; 
    private String startup_time;
    private String hours_up;
    private String days_up;             

    // Constructor para sessões normais
    public Sessao(int sid, String sql_id, String username, String osuser, String program, String status, String machine, int seconds_in_wait, String sql_text) {
        this.sid = sid;
        this.sql_id = sql_id != null ? sql_id : "Desconhecido";
        this.username = username != null ? username : "Desconhecido";
        this.osuser = osuser != null ? osuser : "Desconhecido"; 
        this.program = program != null ? program : "Desconhecido";
        this.status = status != null ? status : "Desconhecido";
        this.machine = machine != null ? machine : "Desconhecida";
        this.seconds_in_wait = seconds_in_wait;
        this.sql_text = sql_text != null ? sql_text : "Desconhecido";
    }

    
    
    // Constructor para sessões bloqueadas
    public Sessao(int sid, String sql_id, String username, String osuser, String program, String status, String blocking_session, String event, int seconds_in_wait) {
        this.sid = sid;
        this.sql_id = sql_id != null ? sql_id : "Desconhecido";
        this.username = username != null ? username : "Desconhecido";
        this.osuser = osuser != null ? osuser : "Desconhecido"; 
        this.program = program != null ? program : "Desconhecido";
        this.status = status != null ? status : "Desconhecido";
        this.blocking_session = blocking_session != null ? blocking_session : "Desconhecido";
        this.event = event != null ? event : "Desconhecido";
        this.seconds_in_wait = seconds_in_wait;
    }

    // Constructor para tempo de execução do banco
    public Sessao( String startup_time, String hours_up, String days_up) {
        this.startup_time = startup_time != null ? startup_time : "Desconhecido";
        this.hours_up = hours_up != null ? hours_up : "Desconhecido";
        this.days_up = days_up != null ? days_up : "Desconhecido";
        
    }

    // Getters e Setters
    public int getSid() { return sid; }
    public String getSql_id() { return sql_id; }
    public String getUsername() { return username; }
    public String getOsuser() { return osuser; }
    public String getProgram() { return program; }
    public String getStatus() { return status; }
    public String getMachine() { return machine; }
    public int getSeconds_in_wait() { return seconds_in_wait; }
    public String getSql_text() { return sql_text; }
    public String getBlocking_session() { return blocking_session; }
    public String getEvent() { return event; }
    public String getStartup_time() { return startup_time; }
    public String getHours_up() { return hours_up; }
    public String getDays_up() { return days_up; }
}
