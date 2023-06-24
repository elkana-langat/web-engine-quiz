package engine.controller;

import engine.entity.Answer;
import engine.entity.Quiz;
import engine.entity.Response;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping(path = "/{quizId}", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long quizId) {
        Quiz quiz = quizService.getQuizById(quizId);

        if (quiz != null) {
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();

        if (quizzes != null) {
            return new ResponseEntity<>(quizzes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quiz> addQuiz(@Valid @RequestBody Quiz quiz, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(quizService.addQuiz(quiz), HttpStatus.OK);
    }

    @PostMapping(path = "{quizId}/solve", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> answerQuiz(@PathVariable Long quizId, @RequestBody Answer answer) {
        Quiz quiz = quizService.getQuizById(quizId);

        if (quiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Response response = new Response();
            List<Integer> correctAnswer = quiz.getAnswer();

            if (answer.getAnswer() == null || answer.getAnswer().isEmpty()) {
                if (correctAnswer == null || correctAnswer.isEmpty()) {
                    response.setSuccess(true);
                    response.setFeedback("Congratulations, you're right!");
                } else {
                    response.setSuccess(false);
                    response.setFeedback("Wrong answer!, Please try again.");
                }
            } else if (answer.getAnswer().equals(correctAnswer)) {
                response.setSuccess(true);
                response.setFeedback("Congratulations, you're right!");
            } else {
                response.setSuccess(false);
                response.setFeedback("Wrong answer!, Please try again.");
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


}
