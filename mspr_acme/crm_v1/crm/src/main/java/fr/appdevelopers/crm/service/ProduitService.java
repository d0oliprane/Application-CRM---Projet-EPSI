package fr.appdevelopers.crm.service;
import java.awt.*;
import java.awt.image.BufferedImage;

import fr.appdevelopers.crm.domain.Produit;
import fr.appdevelopers.crm.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
public class ProduitService {

    @Autowired
    ProduitRepository produitsRepository;

    public List<Produit> getAllProduit() {
        return (List<Produit>) produitsRepository.findAll();
    }

    public List<String> getDistinctCategories() {
        List<Produit> produits = (List<Produit>) produitsRepository.findAll();
        Set<String> categories = new HashSet<>();
        for (Produit produit : produits) {
            categories.add(produit.getCategorie());
        }
        return new ArrayList<>(categories);
    }

    public List<Produit> listeProduit = new ArrayList<>();
    public Produit getProduitById(int id) {
        return produitsRepository.findById(id).orElse(null);
    }

    public void updateProduit(Produit produit) {
        produitsRepository.save(produit);
    }


    public Produit saveProduit(Produit produit) {
        return produitsRepository.save(produit);
    }


    public String saveImage(MultipartFile imageFile) throws IllegalStateException, IOException {
        String originalFileName = imageFile.getOriginalFilename();
        String fileName = "Produit_"  + UUID.randomUUID() + ".jpg";
        String filePath = System.getProperty("user.dir") + "/crm_v1/crm/src/main/resources/static/images/Produit/" + fileName;
        File dest = new File(filePath);

        BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());

        int targetWidth = 800;
        int targetHeight = 600;
        Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage bufferedResizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        bufferedResizedImage.createGraphics().drawImage(resizedImage, 0, 0, null);

        ImageIO.write(bufferedResizedImage, "jpg", dest);

        return fileName;
    }

    public List<Produit> getProduitsParArchive(boolean actif) {
        if (!actif) {
            return produitsRepository.findAllByArchiveFalse();
        } else {
            return produitsRepository.findAllByArchiveTrue();
        }
    }
}




