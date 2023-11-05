package com.bancom.test.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name="post")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String Text;
    @ManyToOne
    private Usuario usuario;
    @Basic(optional = false)
    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    public Post(){}

    public Post(Integer id, String text, Usuario usuario) {
        this.id = id;
        Text = text;
        this.usuario = usuario;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt= new Date();
    }
}
