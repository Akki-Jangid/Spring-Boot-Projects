package com.Banking.Model.Common;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommonBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence")
    @SequenceGenerator(name = "my_sequence", sequenceName = "my_sequence", initialValue = 100, allocationSize = 1)
    private Long id;
}
