package com.bancom.test.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="usuario")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String cellphone;
    private String name;
    private String lastName;
    private String password;
    @Basic(optional = false)
    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    public Usuario(){}

    public Usuario(Integer id, String cellphone, String name, String lastName, String password) {
        this.id = id;
        this.cellphone = cellphone;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt= new Date();
    }
}
