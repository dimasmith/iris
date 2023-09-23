# 2. Use Postgres as an application database

Date: 2023-09-23

## Status

Accepted

## Context

The Iris application needs a relational database to store its data.

Postgres is a mature, widespread and well-supported open source database with the multitude of features.

## Decision

The application will use Postgres as its database. 
It will replace existing binding to MariaDb.

## Consequences

The support and tooling for Postgres is more mature. 
It would be easier to get help once necessary.

The application is intended for personal use and was deployed on a Raspberry PI.
Postgres supports arm architecture, so the deployment would be easy.

I have less experience with Postgres comparing to MySQL'ish databases.
While that may have negative impact on development speed, it poses an opportunity to learn Postgres better.
