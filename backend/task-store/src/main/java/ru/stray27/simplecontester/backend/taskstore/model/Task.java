package ru.stray27.simplecontester.backend.taskstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task {
    @Id
    @GeneratedValue(generator = "task_sequence")
    @GenericGenerator(
            name = "task_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "task_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_value", value = "1")
            }
    )
    private Long id;

    private String title;
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "task")
    private List<TaskTest> tests;

}
