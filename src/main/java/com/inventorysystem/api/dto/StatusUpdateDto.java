package com.inventorysystem.api.dto;

import com.inventorysystem.api.enums.SupplyOrderStatus;

public class StatusUpdateDto {
	private SupplyOrderStatus status;
	private int orderId;

	public SupplyOrderStatus getStatus() {
		return status;
	}

	public void setStatus(SupplyOrderStatus status) {
		this.status = status;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
