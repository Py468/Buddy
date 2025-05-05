package net.enjoy.springboot.registrationlogin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name = "title")
    private String title;
    @Column(nullable = false,name = "description")
    private String description;
    @Column(nullable = false,name = "price")
    private BigDecimal price;
    @Column(nullable = false)
    private String city;

    public void setCity(String city) {
        // Allowed cities
        List<String> allowedCities = Arrays.asList("Noida", "Greater Noida", "Ghaziabad", "Delhi", "Bulandshahr");
        if (!allowedCities.contains(city)) {
            throw new IllegalArgumentException("Invalid city. Please select a valid city.");
        }
        this.city = city;
    }
    @Column(name = "guests")
    private Integer guests;
    @Column(name = "roomsize")
    private Integer roomsize;
    @Column(name = "contact_details")
    private String contactdetails;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @Transient // Used only for file upload during the request
    private MultipartFile imageFile;


}
