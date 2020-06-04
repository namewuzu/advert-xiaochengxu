package com.dk.entity;

public class OrderKey {
    private String orderId;

    private String reOrderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getReOrderId() {
        return reOrderId;
    }

    public void setReOrderId(String reOrderId) {
        this.reOrderId = reOrderId == null ? null : reOrderId.trim();
    }
}