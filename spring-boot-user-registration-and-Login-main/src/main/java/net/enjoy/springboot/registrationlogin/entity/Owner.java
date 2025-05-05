//package net.enjoy.springboot.registrationlogin.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "owner")
//public class Owner {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "name",nullable = false)
//    private String name;
//    @Column(name = "email",unique = true)
//    private String email;
//    @Column(name = "phone",nullable = false)
//    private String phone;
//    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL)
//    List<Room> rooms;
//
//}
