package com.app.expense.tracker;
import java.math.BigDecimal;
import java.time.LocalDate; 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Daily {
    @Id
    @Column(nullable = false, 
    unique = true)
    private LocalDate date;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal income = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal debt = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal emergency = 
    BigDecimal.ZERO;
    
    //Investments
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal srs = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal ssb = 
    BigDecimal.ZERO;

    //Bills
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal cdac = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal electricity = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal insurances = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal internet = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal mobile = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal mortgage = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal tax = 
    BigDecimal.ZERO;

    //Needs
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal food = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal groceries = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal haircut = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal household = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal medical = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal parents = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal transport = 
    BigDecimal.ZERO;

    //Wants
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal eating_out = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal holiday = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal recreational = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal shopping = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal sports = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal tech = 
    BigDecimal.ZERO;

    //Others
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal tithes = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal wake = 
    BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal wedding = 
    BigDecimal.ZERO;
}