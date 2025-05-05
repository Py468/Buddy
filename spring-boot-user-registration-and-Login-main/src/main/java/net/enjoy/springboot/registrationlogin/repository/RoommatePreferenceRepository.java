package net.enjoy.springboot.registrationlogin.repository;

import net.enjoy.springboot.registrationlogin.entity.RoommatePreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoommatePreferenceRepository extends JpaRepository< RoommatePreference,Long> {
    RoommatePreference findByEmail(String email);
}
