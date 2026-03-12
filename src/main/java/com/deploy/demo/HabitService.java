package com.deploy.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HabitService {

    @Autowired
    private HabitRepository habitRepository;

    @Transactional
    public void deleteByEmailAndTaskName(String email, String taskName) {
        // This must match the name in your Repository interface
        habitRepository.deleteByUserEmailAndTaskName(email, taskName);
    }
}