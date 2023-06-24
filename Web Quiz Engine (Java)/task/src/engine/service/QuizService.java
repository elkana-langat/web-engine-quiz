package engine.service;

import engine.entity.Quiz;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public Quiz addQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public List<Quiz> addQuizzes(List<Quiz> quizzes) {
        return quizRepository.saveAll(quizzes);
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public String deleteQuiz(Long id) {
        quizRepository.deleteById(id);

        return String.format("The quiz with id %s was deleted", id);
    }
}
