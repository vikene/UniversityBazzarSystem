package com.team5.ubs.models;

/**
 * Created by tp on 09/04/18.
 */

public class marketplacemodel {
    public String name;
    public String price;
    public String seller;
    public String description;
    public String lending;
    public String image;

    public marketplacemodel(){

    }

    public marketplacemodel(String name, String price, String seller, String description, String lending,String image){
        this.name = name;
        this.price = price;
        this.seller = seller;
        this.description = description;
        this.lending = lending;
        this.image = image;
    }

}
