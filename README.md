# 🏦 E-Banking Application

## 📖 Introduction
This E-Banking Application is a comprehensive financial transaction management system built in Java that simulates core banking operations. The system supports user management, multi-currency accounts, currency exchange, money transfers, stock trading, and portfolio management. Designed with object-oriented principles and design patterns, it provides a robust foundation for digital banking operations.

## 🎯 General Description
The goal of this project was to create a complete banking simulation that handles real-world financial operations including account management, currency exchanges, stock investments, and social features like friend networks. The application processes commands from files and provides detailed JSON outputs for all operations, making it suitable for integration with frontend applications or API services.

## 🛠️ Software Design

### Architecture Overview
The application follows a modular architecture with clear separation of concerns:
- **User Management**: Handle user creation, authentication, and social connections
- **Account System**: Multi-currency account management with automatic balance tracking
- **Transaction Engine**: Currency exchange with real-time rates and transfer processing
- **Investment Platform**: Stock trading with portfolio management and recommendations
- **Observer Pattern**: Real-time notifications for account activities

### Core Components
- **Factory Pattern**: `ContFactory` for creating different account types
- **Builder Pattern**: `UtilizatorBuilder` for flexible user creation
- **Observer Pattern**: Account monitoring and notification system
- **Data Processing**: CSV file parsing for exchange rates and stock values

## 💻 Technologies & Features

### Development Environment
- **Language**: Java
- **Build System**: Gradle
- **Testing**: JUnit framework
- **File Processing**: BufferedReader for CSV parsing

### Key Features
- **User Management**: Create users with email, name, and address validation
- **Friend System**: Add friends with bidirectional relationships
- **Multi-Currency Accounts**: Support for multiple currencies (USD, EUR, GBP, JPY, CAD, etc.)
- **Currency Exchange**: Real-time exchange rates with commission fees for large transactions
- **Money Transfers**: Secure transfers between users in the same currency
- **Stock Trading**: Buy stocks using USD accounts with real stock price data
- **Investment Recommendations**: SMA-based stock analysis for buy recommendations
- **Portfolio Management**: Track stock holdings and account balances
- **JSON Output**: Structured data format for easy integration

### Business Logic
- **Exchange Fees**: 1% commission on exchanges ≥50% of account balance
- **Stock Recommendations**: Simple Moving Average (SMA) analysis comparing 5-day vs 10-day trends
- **Transfer Validation**: Comprehensive checks for account existence and sufficient funds
- **Data Integrity**: Robust error handling and validation throughout the system

## 🗃️ Data Management

### Input Files
1. **Exchange Rates** (`exchangeRates.csv`): Currency conversion rates matrix
2. **Stock Values** (`stockValues.csv`): Historical stock prices for 10 companies
3. **Commands** (`commands.txt`): Batch operations for system testing

### Supported Commands
- `CREATE USER`: Register new users with personal information
- `ADD FRIEND`: Establish friend connections between users
- `ADD ACCOUNT`: Create new currency accounts for users
- `ADD MONEY`: Deposit funds into specific currency accounts
- `EXCHANGE MONEY`: Convert between currencies with rate calculations
- `TRANSFER MONEY`: Send money between users in same currency
- `BUY STOCKS`: Purchase company stocks using USD accounts
- `RECOMMEND STOCKS`: Get AI-powered stock recommendations
- `LIST USER`: Display user information and friend networks
- `LIST PORTFOLIO`: Show complete portfolio with stocks and balances

## 📊 Investment Features

### Stock Trading System
The application supports trading stocks from major companies with real price data:
- Real-time stock price processing from CSV data
- USD-only stock purchases (industry standard)
- Portfolio tracking with quantity and value calculations
- Comprehensive stock recommendations based on technical analysis

### Recommendation Algorithm
- **SMA Strategy**: Compares 5-day and 10-day Simple Moving Averages
- **Buy Signal**: When short-term SMA > long-term SMA (upward trend)
- **JSON Output**: Structured recommendations for easy consumption

## 🏗️ Design Patterns Implementation

### Factory Pattern
```java
ContFactory contFactory = ContFactory.getInstance();
Cont newAccount = contFactory.createCont(AccountType.valueOf(currency), user);
```

### Builder Pattern
```java
Utilizator newUser = new UtilizatorBuilder()
    .setEmail(email)
    .setFirstName(firstName)
    .setLastName(lastName)
    .setAddress(address)
    .build();
```

### Observer Pattern
```java
Observer accountObserver = new Observer(newAccount);
```

## 📦 Installation & Usage

### Prerequisites
- Java 11 or higher
- Gradle build system

### Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone [repository-url]
   cd E-Banking-App
   ```

2. **Build the Project**:
   ```bash
   ./gradlew build
   ```

3. **Run the Application**:
   ```bash
   ./gradlew run --args="common/exchangeRates.csv test1/stockValues.csv test1/commands.txt"
   ```

### File Structure
```
src/main/resources/
├── common/
│   └── exchangeRates.csv
├── test1/
│   ├── commands.txt
│   ├── output.txt
│   └── stockValues.csv
└── [additional test directories]
```

## 🧪 Testing

### Test Coverage
- **Unit Tests**: Comprehensive testing of core functionality
- **Integration Tests**: End-to-end command processing validation
- **Edge Cases**: Error handling and boundary condition testing
- **GitHub Actions**: Automated testing pipeline

### Sample Test Scenarios
- User creation and validation
- Account management operations
- Currency exchange calculations
- Stock trading workflows
- Portfolio management features

## 🚀 Future Enhancements
- **Web API**: REST endpoints for frontend integration
- **Database Integration**: Persistent data storage with MySQL/PostgreSQL
- **Real-time Updates**: WebSocket integration for live price feeds
- **Advanced Analytics**: Machine learning for better stock recommendations
- **Mobile App**: React Native or Flutter frontend
- **Security Features**: Authentication, encryption, and audit logging
- **Microservices**: Split into account, trading, and user services
- **Blockchain Integration**: Cryptocurrency trading capabilities

## 📈 Performance Considerations
- **Memory Management**: Efficient object lifecycle management
- **File Processing**: Optimized CSV parsing for large datasets
- **Scalability**: Modular design for horizontal scaling
- **Caching**: Exchange rate caching for improved performance

## 📝 Conclusion
This E-Banking Application demonstrates advanced Java programming concepts including design patterns, file processing, financial calculations, and comprehensive error handling. The modular architecture makes it highly extensible for additional banking features, while the JSON output format ensures easy integration with modern web and mobile applications.

## 📚 Technical Highlights
- **Object-Oriented Design**: Clean class hierarchy with proper encapsulation
- **Design Patterns**: Factory, Builder, and Observer pattern implementations
- **Financial Logic**: Accurate currency exchange and stock trading calculations
- **Data Processing**: Robust CSV parsing and command interpretation
- **Error Handling**: Comprehensive validation and user-friendly error messages
- **Testing**: Complete test suite with automated validation
