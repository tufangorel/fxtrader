# Fx Trader Application
 - Calculate amount for buy and sell orders
 - Get rank of instructions
 
# Run with
 Run: `fxtrader > mvn clean install`
 
# System Entities

 - Instruction : Main entity for processing
 - Currency : Currency enum for each instruction
 - OrderType : Order type for each instruction
 
# System Services

 - FxTraderCalculatorService : Calculate amount for specific operation
 - FxTraderReportService : Generate report by order type and amount 
 
# Unit Test Classes : 

 - TestFxTraderCalculatorService : Contains test cases for amount calculation for specific days and currencies
 - TestFxTraderReportService : Contains test cases for report generation
 
# Notes :
 - There is no main method in this project. 
 - There 2 main services and 2 test classes for this service classes. 