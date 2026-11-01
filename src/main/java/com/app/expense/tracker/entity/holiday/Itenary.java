package com.app.expense.tracker.entity.holiday;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itenary")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Itenary {
    @Id 
    @Column(nullable = 
    false, 
    unique = 
    true)
    private LocalDate date;

    @Column(nullable = 
    false, 
    unique = 
    true)
    private LocalTime time;

    @ManyToOne(fetch = 
    FetchType
    .LAZY)
    @JoinColumn(name = 
    "country_id", 
    nullable = 
    false)
    private Country country;

    @ManyToOne(fetch = 
    FetchType
    .LAZY)
    @JoinColumn(name = 
    "cityState_id", 
    nullable = 
    false)
    private CityState cityState;

    @Column(nullable = 
    true, 
    unique = 
    true)
    private String busOrFlightNo;

    @Column(nullable = 
    true, 
    unique = 
    true)
    private String busOrTrainStation;

    @Column(nullable = 
    true, 
    unique = 
    true)
    private String venue;

    @Column(nullable = 
    true, 
    unique = 
    true)
    private String address;

    @Column(nullable = 
    true, 
    unique = 
    true)
    private String purpose;

    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal amount = 
    BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, 
    scale = 2)
    private BigDecimal total = 
    BigDecimal.ZERO;
}