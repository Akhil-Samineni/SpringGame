package com.example.game.repository;

import com.example.game.model.Badminton;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadmintonRepository extends JpaRepository<Badminton, Long> {
    @Query("SELECT b FROM Badminton b WHERE b.bookingDate = :bookingDate")
    Optional<Badminton> getByCurrentDate(@Param("bookingDate") String bookingDate);

    @Query("SELECT b FROM Badminton b WHERE b.bookingDate != :bookingDate")
    Optional<List<Badminton>> getStaleData(@Param("bookingDate") String bookingDate);
}
