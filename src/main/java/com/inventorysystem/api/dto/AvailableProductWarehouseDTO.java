package com.inventorysystem.api.dto;

import com.inventorysystem.api.enums.Location;
import com.inventorysystem.api.model.Product;
import com.inventorysystem.api.model.ProductWarehouse;
import org.springframework.stereotype.Component;

@Component
public class AvailableProductWarehouseDTO {

    private Product product;
    private ProductWarehouse productWarehouse;
    private Location location;
    private int quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductWarehouse getProductWarehouse() {
        return productWarehouse;
    }

    public void setProductWarehouse(ProductWarehouse productWarehouse) {
        this.productWarehouse = productWarehouse;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "AvailableProductWarehouseDTO{" +
                "product=" + product +
                ", productWarehouse=" + productWarehouse +
                ", location=" + location +
                ", quantity=" + quantity +
                '}';
    }
}
