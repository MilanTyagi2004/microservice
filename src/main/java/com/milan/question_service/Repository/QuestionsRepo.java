package com.milan.question_service.Repository;

import com.milan.question_service.Entity.Difficulty;
import com.milan.question_service.Entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionsRepo extends JpaRepository<Questions,Integer> {
    List<Questions> findByCategory(String category);
    List<Questions> findByCategoryAndDifficulty(String category, Difficulty difficulty);

    @Query(value = "SELECT q.id FROM questions q WHERE q.category = :category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionByCategory(@Param("category") String category, @Param("numQ") int numQ);
}
