package com.vectrosafe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "nomor_xl")
public class NomorXL {
    @Id
    private Long id;

    @Column(name = "no_hp")
    private String no_hp;

    @Column(name = "pulsa")
    private Long pulsa;

    @Column(name = "masa_aktif")
    private Date masa_aktif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public Long getPulsa() {
        return pulsa;
    }

    public void setPulsa(Long pulsa) {
        this.pulsa = pulsa;
    }

    public Date getMasa_aktif() {
        return masa_aktif;
    }

    public void setMasa_aktif(Date masa_aktif) {
        this.masa_aktif = masa_aktif;
    }
}
