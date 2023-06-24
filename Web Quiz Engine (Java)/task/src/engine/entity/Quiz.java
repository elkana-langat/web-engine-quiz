package engine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String title;
    @NotBlank
    private String text;

    @NotNull
    @Size(min = 2)
    @ElementCollection
    @CollectionTable(name = "quiz_options", joinColumns = @JoinColumn(name = "quiz_id"))
    private List<String> options;

    @ElementCollection
    @CollectionTable(name = "quiz_answers", joinColumns = @JoinColumn(name = "quiz_id"))
    private List<Integer> answer;
}
