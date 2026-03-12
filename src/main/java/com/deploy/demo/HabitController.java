package com.deploy.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habits")
@CrossOrigin(origins = "*")
public class HabitController {

    @Autowired
    private HabitRepository habitRepository;

    // FIX: Added this line to resolve the "cannot find symbol" error
    @Autowired
    private HabitService habitService;

    // 1. TOGGLE (CHECK/UNCHECK) A HABIT
    @PostMapping("/toggle")
    @Transactional
    public ResponseEntity<?> toggleHabit(@RequestBody HabitRequest request) {
        System.out.println("Processing toggle for: " + request.getTaskName() + " on " + request.getMonth() + " "
                + request.getDay());

        Optional<HabitData> existing = habitRepository.findByUserEmailAndTaskNameAndMonthNameAndDayVal(
                request.getEmail(), request.getTaskName(), request.getMonth(), request.getDay());

        if (existing.isPresent()) {
            existing.ifPresent(habitRepository::delete);
            habitRepository.flush();
            System.out.println("SUCCESS: Removed from database.");
            return ResponseEntity.ok("Habit Unchecked");
        } else {
            HabitData newHabit = new HabitData();
            newHabit.setUserEmail(request.getEmail());
            newHabit.setTaskName(request.getTaskName());
            newHabit.setMonthName(request.getMonth());
            newHabit.setDayVal(request.getDay());

            habitRepository.saveAndFlush(newHabit);
            System.out.println("SUCCESS: Inserted into database.");
            return ResponseEntity.ok("Habit Checked");
        }
    }

    // 2. GET ALL HABITS FOR A USER
    @GetMapping("/{email}")
    public ResponseEntity<List<HabitData>> getHabits(@PathVariable String email) {
        List<HabitData> userHabits = habitRepository.findByUserEmail(email);
        return ResponseEntity.ok(userHabits);
    }

    // 3. DELETE A HABIT PERMANENTLY
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteHabit(@RequestParam String email, @RequestParam String taskName) {
        try {
            // This calls the transactional service you already created
            habitService.deleteByEmailAndTaskName(email, taskName);
            System.out.println("SUCCESS: Deleted task " + taskName + " for " + email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}