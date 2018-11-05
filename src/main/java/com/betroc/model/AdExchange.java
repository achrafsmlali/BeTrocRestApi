package com.betroc.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class AdExchange extends Advertisement{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @NotBlank
//    @Size(max = 100)
//    private String title;
//
//    @NotBlank
//    @Size(max = 1500)
//    private String description;
//

    private float estimatedPrice;

    public AdExchange(){

    }

    public AdExchange(@NotNull String email, float estimatedPrice) {
        super(email);
        this.estimatedPrice = estimatedPrice;
    }

    public float getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(float estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    //
//    @Temporal( TemporalType.TIMESTAMP )
//    @CreationTimestamp
//    private Date creationDate;
//    @NotBlank
//    private String adress;
////    private int userId;
////    private int categoryId;
////    private String Etat;
////
//
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public float getEstimatedPrice() {
//        return estimatedPrice;
//    }
//
//    public void setEstimatedPrice(float estimatedPrice) {
//        this.estimatedPrice = estimatedPrice;
//    }
//
//    public Date getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(Date creationDate) {
//        this.creationDate = creationDate;
//    }
//
//    public String getAdress() {
//        return adress;
//    }
//
//    public void setAdress(String adress) {
//        this.adress = adress;
//    }
}
