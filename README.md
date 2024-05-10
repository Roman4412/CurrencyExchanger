#  Currency Exchanger API
## Overview
Currency Exchanger API allows you to quickly and easily perform conversions between different currencies,
as well as manage the list of available currencies and exchange rates.
## Technologies and tools
 - Java 17
 - Maven
 - JDBC
 - HikariCP
 - SQLite
 - Jackson
 - Jakarta Servlet API
 - Tomcat
## Available endpoints
### Currency
**GET** ```/currencies``` - to get all currencies
```
[
    {
        "code": "MNT",
        "name": "Mongolia Tughrik\t",
        "sign": "₮"
    },
    {
        "code": "RUB",
        "name": "Russia Ruble",
        "sign": "₽"
    },
    {
        "code": "THB",
        "name": "Thailand Baht",
        "sign": "฿"
    }
]
```

**GET** ```/currency/USD``` - to get specific currency
```
{
    "code": "USD",
    "name": "United States Dollar",
    "sign": "$"
}
```
**POST** ```/currencies``` - to create a new currency
```
{
    "code": "GEL",
    "name": "Georgian lari",
    "sign": "₾"
}
```
### Exchange rates
**GET** ```/exchangeRates``` - to get all exchange rates
```
[
    {
        "id": 32,
        "baseCurrency": {
            "code": "RUB",
            "name": "Russia Ruble",
            "sign": "₽"
        },
        "targetCurrency": {
            "code": "MNT",
            "name": "Mongolia Tughrik\t",
            "sign": "₮"
        },
        "rate": 10
    },
    {
        "id": 34,
        "baseCurrency": {
            "code": "THB",
            "name": "Thailand Baht",
            "sign": "฿"
        },
        "targetCurrency": {
            "code": "USD",
            "name": "United States Dollar",
            "sign": "$"
        },
        "rate": 111
    }
]
```
**GET** ```/exchangeRate/THBUSD``` - to get specific exchange rate
```
{
        "id": 34,
        "baseCurrency": {
            "code": "THB",
            "name": "Thailand Baht",
            "sign": "฿"
        },
        "targetCurrency": {
            "code": "USD",
            "name": "United States Dollar",
            "sign": "$"
        },
        "rate": 111
    }
```
**POST** ```/exchangeRate``` - to create a new exchange rate
```
{
    "id": 37,
    "baseCurrency": {
        "code": "RUB",
        "name": "Russia Ruble",
        "sign": "₽"
    },
    "targetCurrency": {
        "code": "GEL",
        "name": "Georgian lari",
        "sign": "₾"
    },
    "rate": 12.34
}
```
**PUT** ```/exchangeRate/USDEUR``` - to change the exchange rate
```
{
    "id": 37,
    "baseCurrency": {
        "code": "RUB",
        "name": "Russia Ruble",
        "sign": "₽"
    },
    "targetCurrency": {
        "code": "GEL",
        "name": "Georgian lari",
        "sign": "₾"
    },
    "rate": 11.45
}
```
### Currency exchange
**GET** ```/exchange?from=RUB&to=USD&amount=150``` - currency conversion
```
{
    "exchangeRate": {
        "id": 33,
        "baseCurrency": {
            "code": "RUB",
            "name": "Russia Ruble",
            "sign": "₽"
        },
        "targetCurrency": {
            "code": "USD",
            "name": "United States Dollar",
            "sign": "$"
        },
        "rate": 2134.56
    },
    "amount": 150,
    "convertedAmount": 320184.00
}
```
