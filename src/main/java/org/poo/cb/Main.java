package org.poo.cb;

import java.io.*;
import java.util.*;
public class Main {
    private static Map<String, Utilizator> users = new HashMap<>();
    private static Map<String, List<Cont>> userAccounts = new HashMap<>();
    private static Map<String, Map<String, Double>> exchangeRates = new HashMap<>();
    private static Map<String, List<Double>> stockValues = new HashMap<>();
    private static ContFactory contFactory = ContFactory.getInstance();
    public static void main(String[] args) {


        if (args == null || args.length == 0) {
            System.out.println("Running Main");
            return;
        }

        String exchangeRatesFile = "src/main/resources/" + args[0];
        String stockValuesFile = "src/main/resources/" + args[1];
        String commandsFile = "src/main/resources/" + args[2];

        processExchangeRates(exchangeRatesFile);
        processStockValues(stockValuesFile);
        processCommands(commandsFile);
        users.clear();
        userAccounts.clear();
        exchangeRates.clear();
        stockValues.clear();
    }
    private static void processExchangeRates(String exchangeRatesFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(exchangeRatesFile))) {
            String line;
            line = reader.readLine();
            String[] currencies = line.split(",");
            for (int i = 1; i < currencies.length; i++) {
                line = reader.readLine();
                String[] rates = line.split(",");
                Map<String, Double> rate = new HashMap<>();
                for(int j = 1; j < rates.length; j++) {
                    rate.put(currencies[j], Double.parseDouble(rates[j]));
                }
                exchangeRates.put(currencies[i], rate);
            }
        } catch (IOException e) {
            System.out.println("Error reading exchange rates file: " + e.getMessage());
        }
    }

    private static void processStockValues(String stockValuesFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(stockValuesFile))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String stockName = tokens[0];
                List<Double> values = new ArrayList<>();
                for (int i = 1; i < tokens.length; i++) {
                    values.add(Double.parseDouble(tokens[i]));
                }
                stockValues.put(stockName, values);
            }
        } catch (IOException e) {
            System.out.println("Error reading stock values file: " + e.getMessage());
        }
    }
    private static void processCommands(String commandsFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(commandsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processCommand(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void processCommand(String command) {
        String[] parts = command.split(" ");
        switch (parts[0]) {
            case "CREATE":
                StringBuilder address = new StringBuilder();
                for(int i = 5; i < parts.length - 1; i++) {
                    address.append(parts[i]);
                    address.append(" ");
                }
                address.append(parts[parts.length - 1]);
                createUser(parts[2], parts[3], parts[4], address.toString());
                break;
            case "ADD":
                if (parts[1].equals("FRIEND")) {
                    addFriend(parts[2], parts[3]);
                } else if (parts[1].equals("ACCOUNT")) {
                    addAccount(parts[2], parts[3]);
                } else if (parts[1].equals("MONEY")) {
                    addMoney(parts[2], parts[3], Double.parseDouble(parts[4]));
                }
                break;
            case "EXCHANGE":
                exchangeMoney(parts[2], parts[3], parts[4], Double.parseDouble(parts[5]));
                break;
            case "TRANSFER":
                transferMoney(parts[2], parts[3], parts[4], Double.parseDouble(parts[5]));
                break;
            case "BUY":
                buyStocks(parts[2], parts[3], Integer.parseInt(parts[4]));
                break;
            case "RECOMMEND":
                recommendStocks();
                break;
            case "LIST":
                if (parts[1].equalsIgnoreCase("USER")) {
                    listUser(parts[2]);
                } else if (parts[1].equalsIgnoreCase("PORTFOLIO")) {
                    listPortfolio(parts[2]);
                }
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    private static void createUser(String email, String firstName, String lastName, String address) {
        if (users.containsKey(email)) {
            System.out.println("User with " + email + " already exists");
        } else {
            Utilizator newUser = new UtilizatorBuilder()
                    .setEmail(email)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setAddress(address)
                    .build();
            users.put(email, newUser);
        }
    }

    private static void addFriend(String userEmail, String friendEmail) {
        if (!users.containsKey(userEmail)) {
            System.out.println("User with " + userEmail + " doesn’t exist");
        } else if (!users.containsKey(friendEmail)) {
            System.out.println("User with " + friendEmail + " doesn’t exist");
        } else if (users.get(userEmail).getFriends().contains(users.get(friendEmail))) {
            System.out.println("User with " + friendEmail + " is already a friend");
        } else {
            users.get(userEmail).addFriend(users.get(friendEmail));
            users.get(friendEmail).addFriend(users.get(userEmail));
        }
    }

    private static void addAccount(String userEmail, String currency) {
        if (!users.containsKey(userEmail)) {
            System.out.println("User with email " + userEmail + " doesn’t exist");
        } else {
            Utilizator user = users.get(userEmail);
            Cont newAccount = contFactory.createCont(AccountType.valueOf(currency), user);
            Observer accountObserver = new Observer(newAccount);
            if (userAccounts.containsKey(userEmail)) {
                List<Cont> accounts = userAccounts.get(userEmail);
                for (Cont account : accounts) {
                    if (account.getCurrency().equals(currency)) {
                        System.out.println("Account in currency " + currency + " already exists for user");
                        return;
                    }
                }
                accounts.add(newAccount);
            } else {
                List<Cont> accounts = new ArrayList<>();
                accounts.add(newAccount);
                userAccounts.put(userEmail, accounts);
            }
        }
    }

    private static void addMoney(String userEmail, String currency, double amount) {
        if (!users.containsKey(userEmail)) {
            System.out.println("User with email " + userEmail + " doesn’t exist");
        } else {
            List<Cont> accounts = userAccounts.get(userEmail);
            if (accounts != null) {
                for (Cont account : accounts) {
                    if (account.getCurrency().equals(currency)) {
                        account.deposit(amount);
                        return;
                    }
                }
            }
            System.out.println("Account in currency " + currency + " doesn’t exist for user");
        }
    }

    private static void exchangeMoney(String userEmail, String sourceCurrency, String destinationCurrency, double amount) {
        if (!users.containsKey(userEmail)) {
            System.out.println("User with email " + userEmail + " doesn’t exist");
            return;
        }
        List<Cont> accounts = userAccounts.get(userEmail);
        if (accounts == null) {
            System.out.println("User doesn’t have any accounts");
            return;
        }
        Cont sourceAccount = null;
        Cont destinationAccount = null;
        for (Cont account : accounts) {
            if (account.getCurrency().equals(sourceCurrency)) {
                sourceAccount = account;
            }
            if (account.getCurrency().equals(destinationCurrency)) {
                destinationAccount = account;
            }
        }
        if (sourceAccount == null) {
            System.out.println("Source account in currency " + sourceCurrency + " doesn’t exist for user");
        } else if (destinationAccount == null) {
            System.out.println("Destination account in currency " + destinationCurrency + " doesn’t exist for user");
        } else {
            if(amount * exchangeRates.get(destinationCurrency).get(sourceCurrency) >= 0.5 * sourceAccount.getBalance()) {
                if (sourceAccount.withdraw(1.01 * amount * exchangeRates.get(destinationCurrency).get(sourceCurrency))) {
                    destinationAccount.deposit(amount);
                } else {
                    System.out.println("Insufficient amount in account " + sourceCurrency + " for exchange");
                }
            } else {
                if (sourceAccount.withdraw(amount * exchangeRates.get(destinationCurrency).get(sourceCurrency))) {
                    destinationAccount.deposit(amount);
                } else {
                    System.out.println("Insufficient amount in account " + sourceCurrency + " for exchange");
                }
            }
        }
    }

    private static void transferMoney(String senderEmail, String receiverEmail, String currency, double amount) {
        if (!users.containsKey(senderEmail)) {
            System.out.println("Sender with email " + senderEmail + " doesn’t exist");
            return;
        }
        if (!users.containsKey(receiverEmail)) {
            System.out.println("Receiver with email " + receiverEmail + " doesn’t exist");
            return;
        }
        List<Cont> senderAccounts = userAccounts.get(senderEmail);
        List<Cont> receiverAccounts = userAccounts.get(receiverEmail);
        if (senderAccounts == null || receiverAccounts == null) {
            System.out.println("Sender or receiver doesn't have any accounts");
            return;
        }
        Cont senderAccount = findAccount(senderAccounts, currency);
        Cont receiverAccount = findAccount(receiverAccounts, currency);
        if (senderAccount == null) {
            System.out.println("Sender doesn’t have an account in currency " + currency);
        } else if (receiverAccount == null) {
            System.out.println("Receiver doesn’t have an account in currency " + currency);
        } else {
            if (senderAccount.getBalance() >= amount) {
                senderAccount.withdraw(amount);
                receiverAccount.deposit(amount);
            } else {
                System.out.println("Insufficient amount in account " + currency + " for transfer");
            }
        }
    }

    private static Cont findAccount(List<Cont> accounts, String currency) {
        for (Cont account : accounts) {
            if (account.getCurrency().equals(currency)) {
                return account;
            }
        }
        return null;
    }

    private static void buyStocks(String email, String company, int numberOfStocks) {
        if (!users.containsKey(email)) {
            System.out.println("User with email " + email + " doesn’t exist");
            return;
        }

        Utilizator user = users.get(email);

        double stockPrice = stockValues.get(company).getLast();
        double totalCost = stockPrice * numberOfStocks;
        List<Cont> accounts = userAccounts.get(email);
        Cont usdAccount = null;
        for(Cont account : accounts) {
            if(account.getCurrency().equals("USD"))
                usdAccount = account;
        }

        if (usdAccount.getBalance() < totalCost) {
            System.out.println("Insufficient amount in account for buying stock");
            return;
        }
        usdAccount.withdraw(totalCost);
        user.getPortfolio().addStock(new Actiuni(company, Arrays.asList(stockPrice), numberOfStocks));
    }
    private static void recommendStocks() {
        List<String> recommendedStocks = new ArrayList<>();
        for(Map.Entry<String, List<Double>> entry : stockValues.entrySet()) {
            List<Double> values = entry.getValue();
            Double sma1 = (double) 0;
            Double sma2 = (double) 0;
            for(int i = 0; i < 5; i++) {
                sma2 += values.get(i);
            }
            for(int i = 5; i < 10; i++) {
                sma1 += values.get(i);
                sma2 += values.get(i);
            }
            sma1 /= 5;
            sma2 /= 10;
            if(sma1 > sma2) {
                recommendedStocks.add(entry.getKey());
            }
        }
        StringBuilder output = new StringBuilder();
        output.append("{\"stocksToBuy\":[");
        if(recommendedStocks != null && !recommendedStocks.isEmpty()) {
            for(int i = 0; i < recommendedStocks.size() - 1; i++) {
                output.append("\"");
                output.append(recommendedStocks.get(i));
                output.append("\",");
            }
            output.append("\"");
            output.append(recommendedStocks.getLast());
            output.append("\"");
        }
        output.append("]}");
        System.out.println(output);
    }

    private static void listUser(String email) {
        if (!users.containsKey(email)) {
            System.out.println("User with " + email + " doesn't exist");
        } else {
            Utilizator user = users.get(email);
            StringBuilder output = new StringBuilder();
            output.append("{\"email\":\"");
            output.append(user.getEmail());
            output.append("\",\"firstname\":\"");
            output.append(user.getFirstName());
            output.append("\",\"lastname\":\"");
            output.append(user.getLastName());
            output.append("\",\"address\":\"");
            output.append(user.getAddress());
            output.append("\",\"friends\":[");
            if(user.getFriends() != null && !user.getFriends().isEmpty()) {
                for (int i = 0; i < user.getFriends().size() - 1; i++) {
                    output.append("\"");
                    output.append(user.getFriends().get(i).getEmail());
                    output.append("\",");
                }
                output.append("\"");
                output.append(user.getFriends().getLast().getEmail());
                output.append("\"");
            }
            output.append("]}");
            System.out.println(output);
        }
    }

    private static void listPortfolio(String email) {
        if (!users.containsKey(email)) {
            System.out.println("User with email " + email + " doesn’t exist");
        } else {
            Utilizator user = users.get(email);
            List<Cont> accounts = userAccounts.get(email);
            List<Actiuni> stocks = user.getPortfolio().getStocks();
            StringBuilder output = new StringBuilder();
            output.append("{\"stocks\":[");
            if(stocks != null && !stocks.isEmpty()) {
                for(int i = 0; i < stocks.size() - 1; i++) {
                    output.append("{\"stockName\":\"");
                    output.append(stocks.get(i).getCompanyName());
                    output.append("\",\"amount\":");
                    output.append(stocks.get(i).getAmount());
                    output.append("},");
                }
                output.append("{\"stockName\":\"");
                output.append(stocks.getLast().getCompanyName());
                output.append("\",\"amount\":");
                output.append(stocks.getLast().getAmount());
                output.append("}");
            }
            output.append("],\"accounts\":[");
            if(accounts != null && !accounts.isEmpty()) {
                for(int i = 0; i < accounts.size() - 1; i++) {
                    output.append("{\"currencyName\":\"");
                    output.append(accounts.get(i).getCurrency());
                    output.append("\",\"amount\":\"");
                    output.append(String.format("%.2f", accounts.get(i).getBalance()));
                    output.append("\"},");
                }
                output.append("{\"currencyName\":\"");
                output.append(accounts.getLast().getCurrency());
                output.append("\",\"amount\":\"");
                output.append(String.format("%.2f", accounts.getLast().getBalance()));
                output.append("\"}");
            }
            output.append("]}");
            System.out.println(output);
        }
    }
}