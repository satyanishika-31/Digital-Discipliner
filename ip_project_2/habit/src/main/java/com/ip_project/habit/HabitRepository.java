package com.ip_project.habit;

import java.util.List;
import java.util.Optional; // Required

import org.springframework.data.jpa.repository.JpaRepository; // Required
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface HabitRepository extends JpaRepository<HabitData, Long> {
    
    Optional<HabitData> findByUserEmailAndTaskNameAndMonthNameAndDayVal(
        String userEmail, String taskName, String monthName, int dayVal
    );

    List<HabitData> findByUserEmail(String userEmail);

    // ADD THESE TWO ANNOTATIONS
    @Modifying
    @Transactional
    void deleteByUserEmailAndTaskName(String userEmail, String taskName);
}