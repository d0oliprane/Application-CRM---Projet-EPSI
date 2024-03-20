package fr.appdevelopers.crm.domain;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Random;

@Entity
@Table(name = "Produit")
@Component
public class Produit implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private int idProduit;
    @Column(name = "nom")
    private String nom;
    @Column(name = "description")
    private String description;
    @Column(name = "stock")
    private int stock;
    @Column(name = "prix")
    private float prix;
    @Column(name = "image")
    private String image;
    @Column(name = "categorie")
    private String categorie;
    @Column(name = "archive")
    private boolean archive;
    @Transient
    MultipartFile imageFile;

    public Produit(int idProduit, String nom, String description, int stock, float prix, String image, String categorie, boolean archive) {
        this.idProduit = idProduit;
        this.nom = nom;
        this.description = description;
        this.stock = stock;
        this.prix = prix;
        this.image = image;
        this.categorie = categorie;
        this.archive = archive;
    }
    public Produit() {
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }


    public MultipartFile getImageFile() {
        return imageFile;
    }


    public Produit(String categorie) {
        this.categorie = categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }


    public void setArchive(boolean archive) {
        this.archive = archive;
    }


    public int getIdProduit() {
        return idProduit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }


}
