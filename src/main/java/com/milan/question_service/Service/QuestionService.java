package com.milan.question_service.Service;


import com.milan.question_service.Entity.Difficulty;
import com.milan.question_service.Entity.Questions;
import com.milan.question_service.Repository.QuestionsRepo;
import com.milan.question_service.dto.QuestionDTO;
import com.milan.question_service.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionsRepo questionsRepo;
    public List<Questions> getAllQuestion(){
        return questionsRepo.findAll();
    }

    public List<Questions> getCategoryQuestion(String category){
        return questionsRepo.findByCategory(category);
    }
    public List<Questions> getDifficultyAndCategoryQuestion(String category, Difficulty difficulty){
        return questionsRepo.findByCategoryAndDifficulty(category,difficulty);
    }
    public void addQuestions(Questions questions){
        questionsRepo.save((questions));
    }

    public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryname, Integer numQuestions) {
        List<Integer> questions = questionsRepo.findRandomQuestionByCategory(categoryname, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionDTO>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        List<Questions> questions= new ArrayList<>();

        for(Integer id: questionIds){
            questions.add(questionsRepo.findById(id).get());
        }
        for(Questions questions1:questions){
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(questions1.getId());
            questionDTO.setQuestionTitle(questions1.getQuestionTitle());
            questionDTO.setOptions(questions1.getOptions());
            questionDTOS.add(questionDTO);
        }
        return new ResponseEntity<>(questionDTOS,HttpStatus.OK);
    }

    public int getScore(List<Response> responses) {
        int right = 0;
        for (Response response : responses) {
            Questions questions = questionsRepo.findById(response.getId()).get();
            if (response.getResponse().equalsIgnoreCase(questions.getCorrectAnswer()))
                right++;
        }
        return right;
    }
}
