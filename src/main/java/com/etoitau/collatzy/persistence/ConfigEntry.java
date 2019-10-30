package com.etoitau.collatzy.persistence;


import com.etoitau.collatzy.domain.CollatzConfig;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="configs")
public class ConfigEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="d")
    private int d;

    @Column(name="m")
    private int m;

    @Column(name="p")
    private int p;

    @Column(name="nodes")
    private String jsonNodes;

    public ConfigEntry() {
        this.d = 2;
        this.m = 3;
        this.p = 1;
        this.jsonNodes = "";
    }

    public ConfigEntry(int d, int m, int p) {
        this.d = d;
        this.m = m;
        this.p = p;
        this.jsonNodes = "";
    }

    public ConfigEntry(CollatzConfig config) {
        this.d = config.getD();
        this.m = config.getM();
        this.p = config.getP();
        this.jsonNodes = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public String getJsonNodes() {
        return jsonNodes;
    }

    public void setJsonNodes(String jsonNodes) {
        this.jsonNodes = jsonNodes;
    }
}
