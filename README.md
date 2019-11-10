Iris
====

![](https://github.com/dimasmith/iris/workflows/CI/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=dimasmith_iris&metric=alert_status)](https://sonarcloud.io/dashboard?id=dimasmith_iris)

Iris is the service that simplifies financial tasks.
I'm developing it for personal needs, so it is rather simple. The service does not even have any security now. It's more an example project to work with hexagonal architecture.

## Features
### Implemented
* Settle Balances - check balances of the banking account and the balance in the accounting system. Display both balances and show whether those are equal.

### Planned
* Remind of Unsettled Balance - the service performs balance checks on schedule and notifies user if there is misbalance.
* Manage Periodic Payments - manage a list of subscriptions and periodic payments. It helps to plan a monthly budget.
* Calculate Taxes - prepare numbers for a tax declaration for the last quarter.

## Integrations
### Monobank
It is possible to retrieve banking accounts and balances from the [Monobank](https://monobank.ua).

### Homemoney
The [Homemoney](https://homemoney.ua) is a personal accounting service. 
Iris can retrieve accounts and balances from the Homemoney.

To enable integration first you need to retrieve a token from the [Homemoney API](https://homemoney.ua/api/).
After that, configure properties to enable Homemoney accounting.

```yaml
iris:
  accounting: homemoney # use Homemoney as accounting
  homemoney:
    token: <token> # API token for Homemoney
    currencies: # map currency IDs from Homemoney to ISO currency codes
      808080: UAH  
      808081: USD  
      808082: EUR  
```
The tricky part is the currency. 
Homemoney does not use any standard codes for a currency.
Only internal ID is present in a payload. 
This ID is not consistent between users, so each user has their own mapping.
There's also no documented endpoint to get all the currencies for the user.

Note that Iris caches requests to Homemoney API for 5 minutes. 