package com.example.bookservice.requestmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookRequestModel {

    @Positive(message = "Year must be positive")
    @Min(value = 1850, message = "The min year must be 1850")
    @Max(value = 2022, message = "The max year must be less than current year")
    private Integer year;

    @NotNull(message = "title is required")
    private String title;

    @Positive(message = "Price must be positive")
    @NotNull(message = "price is required")
    private Double price;

    @NotNull(message = "author name is required")
    private String author;

    @NotNull(message = "Stock number is required")
    @Positive(message = "Stock number must be greater than 0")
    private Integer stock;

}
