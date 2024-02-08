package org.poo.cb;
import java.util.*;
import java.io.*;
public class Portofoliu {
    private List<Cont> accounts;
    private List<Actiuni> stocks;

    public Portofoliu() {
        this.accounts = new ArrayList<>();
        this.stocks = new ArrayList<>();
    }
    public void addStock(Actiuni stock) {
        stocks.add(stock);
    }
    public List<Actiuni> getStocks() {
        return stocks;
    }
}
