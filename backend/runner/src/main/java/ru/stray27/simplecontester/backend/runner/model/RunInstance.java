package ru.stray27.simplecontester.backend.runner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunInstance {
    @Id
    @GeneratedValue(generator = "run_instance_sequence")
    @GenericGenerator(
            name = "run_instance_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "run_instance_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_value", value = "1")
            }
    )
    private Long id;

    private String username;
    private String language;
    private String version;
    private String stdout;
    private String stderr;
    private Integer code;
}
