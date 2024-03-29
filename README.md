Iris
====

[![Build Status](https://sonarcloud.io/api/project_badges/measure?project=dimasmith_iris&metric=alert_status)](https://sonarcloud.io/dashboard?id=dimasmith_iris)

Iris is the service that simplifies financial tasks.
I'm developing it for personal needs, so it is rather simple. The service does not even have any security now. It's more an example project to work with hexagonal architecture.

## Features
### Implemented
* Settle Balances - check balances of the banking account and the balance in the accounting system. Display both balances and show whether those are equal.

### Planned
* Remind of Unsettled Balance - the service performs balance checks on schedule and notifies user if there is misbalance.
* Manage Periodic Payments - manage a list of subscriptions and periodic payments. It helps to plan a monthly budget.
* Calculate Taxes - prepare numbers for a tax declaration for the last quarter.

## Settings

| Property | Description | Values |
| -------- | ----------- | ------ |
| `iris.banking` | type of banking systems | `demo`, `monobank` |
| `iris.accounting` | type of accounting systems | `demo`, `homemoney` |

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

### Todoist

Iris can report a balance mismatch by reopening a dedicated task in the [Todoist](https://todoist.com).

Enable a Todoist integration by setting `iris.todoist.enabled=true`. 
You need an access token to integrate. 
Obtain one in the integrations section of the Todoist settings screen.
The todoist integration works with a single predefined task. 
When balance settled, Iris completes the task. 
When settlement check fails, Iris reopens the task and sets its due date on today.
Create a task, open its details and supply the task id to the Todoist integration settings.
An example settings are:

```yaml
iris:
  todoist:
    enabled: true
    token: 9cffe052-09f2-47ee-9361-dd640fe96a89
    task-id: 123456
``` 

## Working with the project

### Viewing architecture documents

The project contains architecture description in a `structurizr` format.
Use the [Structurizr Lite](https://structurizr.com/help/lite) to view the documentation.

The easiest way is to use the docker container for Structurizr Lite.

```shell
docker run -it --rm -p 8888:8080 -v "$(pwd)/arch-docs:/usr/local/structurizr" structurizr/lite
```

Open the structurizr ui on [http://localhost:8888](http://localhost:8888)
