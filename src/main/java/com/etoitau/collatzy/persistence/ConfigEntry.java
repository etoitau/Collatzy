package com.etoitau.collatzy.persistence;


import com.etoitau.collatzy.domain.CollatzConfig;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Database row
 * each entry is a Collatz configuration and
 *  all the nodes that have been explored to date for that configuration - represented as a serialized string
 */
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

    @Lob
    @Column(name="nodes", columnDefinition = "CLOB")
    private String serialNodes;

    public ConfigEntry() {
        this.d = 2;
        this.m = 3;
        this.p = 1;
        this.serialNodes = "";
    }

    public ConfigEntry(int d, int m, int p) {
        this.d = d;
        this.m = m;
        this.p = p;
        this.serialNodes = "";
    }

    public ConfigEntry(CollatzConfig config) {
        this.d = config.getD();
        this.m = config.getM();
        this.p = config.getP();
        this.serialNodes = "";
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

    public String getSerialNodes() {
        return serialNodes;
    }

    public void setSerialNodes(String serialNodes) {
        this.serialNodes = serialNodes;
    }
}
