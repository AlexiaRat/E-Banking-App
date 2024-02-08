package org.poo.cb;

enum AccountType {
    EUR, USD, CAD, JPY, GBP
}

public class Cont {
    private String currency;
    private double balance;
    private Observer observer;
    private Utilizator owner;

    public Cont(String currency, Utilizator owner) {
        this.currency = currency;
        this.owner = owner;
        this.balance = 0.0;
    }

    public String getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public Utilizator getOwner() {
        return owner;
    }

    public void deposit(double amount) {
        balance += amount;
        notifyObserver();
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            notifyObserver();
            return true;
        } else {
            return false;
        }
    }
    public void attach(Observer observer) {
        this.observer = observer;
    }
    public void notifyObserver() {
        observer.update();
    }
}

class ContUSD extends Cont {
    public ContUSD(Utilizator owner) {
        super("USD", owner);
    }
}
class ContEUR extends Cont {
    public ContEUR(Utilizator owner) {
        super("EUR", owner);
    }
}
class ContGBP extends Cont {
    public ContGBP(Utilizator owner) {
        super("GBP", owner);
    }
}

class ContJPY extends Cont {
    public ContJPY(Utilizator owner) {
        super("JPY", owner);
    }
}

class ContCAD extends Cont {
    public ContCAD(Utilizator owner) {
        super("CAD", owner);
    }
}

class ContFactory {
    private static ContFactory instance;
    private ContFactory() {

    }

    public static ContFactory getInstance() {
        if(instance == null)
            instance = new ContFactory();
        return instance;
    }

    public Cont createCont(AccountType currency, Utilizator owner) {
        switch (currency) {
            case EUR :
                return new ContEUR(owner);
            case USD:
                return new ContUSD(owner);
            case GBP:
                return new ContGBP(owner);
            case JPY:
                return new ContJPY(owner);
            case CAD:
                return new ContCAD(owner);
        }
        return null;
    }
}
