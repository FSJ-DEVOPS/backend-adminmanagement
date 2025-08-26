package com.kce.admin.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class DailyStat {

    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonProperty("sales")
    private int sales;

    @JsonProperty("returned")
    private int returns;

    @JsonProperty("cancelled")
    private int cancelled;

    public DailyStat() {
    }

    public DailyStat(LocalDate date, int sales, int returns, int cancelled) {
        this.date = date;
        this.sales = sales;
        this.returns = returns;
        this.cancelled = cancelled;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getSales() { return sales; }
    public void setSales(int sales) { this.sales = sales; }

    public int getReturns() { return returns; }
    public void setReturns(int returns) { this.returns = returns; }

    public int getCancelled() { return cancelled; }
    public void setCancelled(int cancelled) { this.cancelled = cancelled; }
}