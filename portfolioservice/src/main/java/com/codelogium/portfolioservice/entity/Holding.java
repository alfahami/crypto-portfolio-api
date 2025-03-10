package com.codelogium.portfolioservice.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "holdings")
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) //symbol must to be unique per portfolio
    @NotBlank(message = "Symbol cannot be null or blank")
    private String symbol;

    @PositiveOrZero(message = "Amount must be greater or equal to zero")
    private BigDecimal amount;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
}
