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

    public void addAccount(Cont account) {
        accounts.add(account);
    }

    public void addStock(Actiuni stock) {
        stocks.add(stock);
    }

    public List<Cont> getAccounts() {
        return accounts;
    }

    public List<Actiuni> getStocks() {
        return stocks;
    }
}
