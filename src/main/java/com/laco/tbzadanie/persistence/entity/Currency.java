package com.laco.tbzadanie.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", columnDefinition = "VARCHAR(255)")
    private UUID uuid;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "code", unique = true)
    private String code;

    @NotNull
    @Column(name = "price")
    private double price;
}
