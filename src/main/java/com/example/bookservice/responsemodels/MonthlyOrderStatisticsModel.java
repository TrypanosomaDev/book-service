package com.example.bookservice.responsemodels;


public interface MonthlyOrderStatisticsModel {
    public String getmonthName();
    public Integer getTotalBookCount();
    public Integer getTotalOrderCount();
    public Double getTotalPurchasedAmount();

}