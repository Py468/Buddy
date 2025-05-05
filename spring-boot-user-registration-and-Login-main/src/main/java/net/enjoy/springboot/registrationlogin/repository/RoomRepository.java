package net.enjoy.springboot.registrationlogin.repository;

import net.enjoy.springboot.registrationlogin.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE LOWER(r.title) LIKE '%flat%'")
    List<Room> findFlats();

    @Query("SELECT r FROM Room r WHERE LOWER(r.title) LIKE '%pg%'")
    List<Room> findPGs();

    @Query("SELECT r FROM Room r WHERE LOWER(r.title) LIKE '%hostel%'")
    List<Room> findHostels();

    @Query("SELECT r FROM Room r WHERE "
            + "(:city IS NULL OR r.city = :city) AND "
            + "(:minPrice IS NULL OR r.price >= :minPrice) AND "
            + "(:maxPrice IS NULL OR r.price <= :maxPrice)")
    List<Room> findByCityAndPriceRange(
            @Param("city") String city,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);

}
