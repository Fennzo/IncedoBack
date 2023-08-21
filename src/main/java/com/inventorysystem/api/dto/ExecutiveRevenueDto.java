package com.inventorysystem.api.dto;

public class ExecutiveRevenueDto {

    private int lastMonthRevenue;
    private int thisMonthRevenue;

    public int getLastMonthRevenue() {
        return lastMonthRevenue;
    }

    public void setLastMonthRevenue(int lastMonthRevenue) {
        this.lastMonthRevenue = lastMonthRevenue;
    }

    public int getThisMonthRevenue() {
        return thisMonthRevenue;
    }

    public void setThisMonthRevenue(int thisMonthRevenue) {
        this.thisMonthRevenue = thisMonthRevenue;
    }
}
