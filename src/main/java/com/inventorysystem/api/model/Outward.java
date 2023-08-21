package com.inventorysystem.api.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Outward {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NonNull
    private LocalDate dateOfDispatch;
    private String invoiceNumber;
    private int quantity;
    private String receiptNumber;
    @OneToOne
    private Customer customer;
    @OneToOne
    private Warehouse warehouse;
    @OneToOne
    private Product product;

    @NonNull
    private int dispatchTurnover;

    @NonNull
    public LocalDate getDateOfDispatch() {
        return dateOfDispatch;
    }

    public int getDispatchTurnover() {
        return dispatchTurnover;
    }

    public void setDispatchTurnover(int dispatchTurnover) {
        this.dispatchTurnover = dispatchTurnover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateOfDispatch(LocalDate now) {
        return dateOfDispatch;
    }

    public void setDateOfDispatch(LocalDate dateOfDispatch) {
        this.dateOfDispatch = dateOfDispatch;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Outward{" +
                "id=" + id +
                ", dateOfDispatch=" + dateOfDispatch +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", quantity=" + quantity +
                ", receiptNumber='" + receiptNumber + '\'' +
                ", customer=" + customer +
                ", warehouse=" + warehouse +
                ", product=" + product +
                '}';
    }
}
