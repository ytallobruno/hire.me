package com.bemobi.UrlShortener.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "url")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "original_url")
    private String originalUrl;

    @NotNull
    @NotEmpty
    @Column(name = "hashed_url")
    private String hashedUrl;

    @NotNull
    @NotEmpty
    @Column(name = "alias")
    private String alias;

    @NotNull
    @Column(name = "access_count")
    private Long accessCount;
}