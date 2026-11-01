package com.app.expense.tracker.entity;
import java.math.BigDecimal;
import com.app.expense.tracker.Enum.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id 
    @GeneratedValue(strategy = 
    GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = 
    false, 
    unique = false)
    private String name;

    @Enumerated(EnumType
    .STRING)
    @Column(nullable = 
    false)
    private AccountType type;

    @Column(unique = 
    true)
    private String account_number;

    @Enumerated(EnumType
    .STRING)
    @Column(nullable = 
    false)
    private AccountStatus status;

    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal amount = 
    BigDecimal.ZERO;
}