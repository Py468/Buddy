package net.enjoy.springboot.registrationlogin.repository;

import net.enjoy.springboot.registrationlogin.entity.RoommateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoommateRequestRepository extends JpaRepository<RoommateRequest,Long> {
    List<RoommateRequest> findByApprovedFalse(); // Unapproved requests
    List<RoommateRequest> findByApprovedTrue();// Approved requests
    RoommateRequest findByEmail(String email);

}
