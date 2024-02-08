package org.poo.cb;
import java.util.*;
import java.io.*;
public class Actiuni {
    private String companyName;
    private List<Double> values;
    private Integer amount;

    public Actiuni(String companyName, List<Double> values, Integer amount) {
        this.companyName = companyName;
        this.values = values;
        this.amount = amount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getAmount() {
        return amount;
    }
}
