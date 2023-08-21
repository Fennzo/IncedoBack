package com.inventorysystem.api.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.inventorysystem.api.dto.AvailableProductWarehouseDTO;
import com.inventorysystem.api.enums.Location;
import com.inventorysystem.api.model.CustomerProduct;
import com.inventorysystem.api.model.Product;
import com.inventorysystem.api.model.ProductWarehouse;
import com.inventorysystem.api.repository.ProductWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductWarehouseService {

    private final GeoApiContext context;

    @Autowired
    public ProductWarehouseService() {
        this.context = new GeoApiContext.Builder()
                .apiKey("AIzaSyA_yaxjGDKLtw8qlGU1FsVTafhZwyYT2PI") // Set your API key here
                .build();
    }

    @Autowired
    private ProductWarehouseRepository productWarehouseRepository;

    public double getCapacityUsageByManagerId(int managerId){
        int currentCapacity = productWarehouseRepository.getCapacityByManagerId(managerId);
        return (double) currentCapacity /1000;
    }

    public ProductWarehouse post(ProductWarehouse productWarehouse){
        return productWarehouseRepository.save(productWarehouse);
    }


    public AvailableProductWarehouseDTO findClosestWarehouse(CustomerProduct customerProduct) {
        List<Object[]> queryResult = productWarehouseRepository.getAvailableProductWarehousesWithProductId(customerProduct.getProduct().getId(), customerProduct.getQuantity());
        List<AvailableProductWarehouseDTO> availableProductWarehouseDTOList = new ArrayList<>();

        for(Object [] result: queryResult){
            AvailableProductWarehouseDTO availableProductWarehouseDTO = new AvailableProductWarehouseDTO();
            availableProductWarehouseDTO.setProduct((Product) result[0]);
            availableProductWarehouseDTO.setProductWarehouse((ProductWarehouse) result[1]);
            availableProductWarehouseDTO.setQuantity((Integer) result[2]);
            availableProductWarehouseDTO.setLocation((Location) result[3]);
            availableProductWarehouseDTOList.add(availableProductWarehouseDTO);
            System.out.println(availableProductWarehouseDTO.toString());
        }

        LatLng origin = getLatLngFromZipCode(customerProduct.getCustomer().getAddress().getZipcode());

        AvailableProductWarehouseDTO closestWarehouse = null;
        double shortestDistance = Double.MAX_VALUE;

        for (AvailableProductWarehouseDTO warehouse : availableProductWarehouseDTOList) {
            Location correspondingLocation = warehouse.getLocation();
            LatLng destination = new LatLng(correspondingLocation.getLatitude(), correspondingLocation.getLongitude());

            DistanceMatrix distanceMatrix = calculateDistanceMatrix(origin, destination);

            if (distanceMatrix.rows.length > 0 &&
                    distanceMatrix.rows[0].elements.length > 0 &&
                    distanceMatrix.rows[0].elements[0].status == DistanceMatrixElementStatus.OK) {
                long distanceValue = distanceMatrix.rows[0].elements[0].distance.inMeters;

                if (distanceValue < shortestDistance) {
                    shortestDistance = distanceValue;
                    closestWarehouse = warehouse;
                }
            }
        }

        return closestWarehouse;
    }

    private LatLng getLatLngFromZipCode(String zipCode) {
        try {
            GeocodingResult[] results = GeocodingApi.newRequest(context)
                    .address(zipCode)
                    .await();

            if (results.length > 0) {
                LatLng location = results[0].geometry.location;
                return new LatLng(location.lat, location.lng);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private DistanceMatrix calculateDistanceMatrix(LatLng origin, LatLng destination) {
        try {
            return DistanceMatrixApi.newRequest(context)
                    .origins(origin)
                    .destinations(destination)
                    .await();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
