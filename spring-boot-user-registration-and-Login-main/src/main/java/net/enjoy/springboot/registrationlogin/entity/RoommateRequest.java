package net.enjoy.springboot.registrationlogin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roommaterequest")
public class RoommateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private String age;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "occupation", nullable = false)
    private String occupation;

    @Column(name = "sleepSchedule")
    private String sleepSchedule;


    @Column(name = "personality")
    private String personality;

    @Column(name = "cleanliness")
    private Integer cleanliness;

    @Column(name = "budgetRange", nullable = false)
    private String budgetRange;

    @Column(name = "accommodationType")
    private String accommodationType;

    @Column(name = "preferredArea")
    private String preferredArea;

    @Column(name = "phone_number", nullable = false)
//    @Pattern(regexp = "^[0-9]{12}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Column(name = "preferred_roommates")
    private Integer preferredRoommates;

    @Column(name = "interests")
    private String  interests;

    @Column(name = "rating_past_roommate")
    private Integer ratingPastRoommate;

    @Column(name = "socialActivityLevel")
    private String socialActivityLevel;
    @Column(name = "email")
    private String email;



    @Column(name = "photo_room", length = 255)
    private String photoRoom;


    @Column(name = "photoRoommate", length = 255)
    private String photoRoommate;


    @Column(name = "approved")
    private Boolean approved = false;

}
