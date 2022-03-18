package ru.stray27.simplecontester.backend.taskstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TaskTest {
    @Id
    @GeneratedValue(generator = "task_test_sequence")
    @GenericGenerator(
            name = "task_test_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "task_test_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_value", value = "1")
            }
    )
    private Long id;

    private String input;
    private String expectedOutput;
    private Boolean visible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
}
