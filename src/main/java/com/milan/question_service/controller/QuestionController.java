package com.milan.question_service.controller;


import com.milan.question_service.Entity.Difficulty;
import com.milan.question_service.Entity.Questions;
import com.milan.question_service.Service.QuestionService;
import com.milan.question_service.dto.QuestionDTO;
import com.milan.question_service.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Questions>> getAllQuestion(){
        try{
        return new ResponseEntity<>(questionService.getAllQuestion(),HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "filter",params = {"category"})
    public ResponseEntity<List<Questions>> getQuestionByCategory(@RequestParam String category){
        try{
            return new ResponseEntity<>(questionService.getCategoryQuestion(category),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "filter",params = {"category","difficulty"})
    public ResponseEntity<List<Questions>> getQuestionsByCategoryAndDifficulty(@RequestParam String category,@RequestParam Difficulty difficulty ){
        try{
            return new ResponseEntity<>(questionService.getDifficultyAndCategoryQuestion(category,difficulty),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("addQuestions")
    public ResponseEntity<?> addQuestions(@RequestBody Questions questions){
        try{
            questionService.addQuestions(questions);
            return new ResponseEntity<>(questions,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
//    generate
    @GetMapping("add")
    public ResponseEntity<List<Integer>>
    getQuestionForQuiz(@RequestParam String category,
                       @RequestParam Integer numQuestions){
        return questionService.getQuestionForQuiz(category,numQuestions);
    }
//   getQuestions(Question id)

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }

//    get score

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        int score  =  questionService.getScore(responses);
        return new ResponseEntity<>(score,HttpStatus.OK);
    }
}
