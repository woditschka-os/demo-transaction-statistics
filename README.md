# Transaction Statistics

[![Build Status](https://travis-ci.org/woditschka-os/demo-transaction-statistics.svg?branch=master)](https://travis-ci.org/woditschka-os/demo-transaction-statistics)

Transaction Statistics provides a restful API for statistics about transactions received within the last 60 seconds.

# Architecture

The application is designed following the principles of [DDD](https://en.wikipedia.org/wiki/Domain-driven_design) with the layers:

* api - rest entry points, translates requests and responsed and delegates to service layer
* service - manage access to the domain model, cleanup and caching
* domain - business logic

## Time complexity requirements

* query statistics with O(1)
* store transactions close to O(1)

## Implementation

To allow performat storage, cleanup and retrieval transactions are stored in a threadsafe time based [min-heap](https://en.wikipedia.org/wiki/Binary_heap) which allows 
to insert new transactions with O(log n), to efficiently cleanup expired transactions and calculate statistics with O(n)

To allow statistics query with O(1) and keep the transaction stream clean these operations are executed periodic with
the result cached for retrieval with O(1). 

# Build instructions

The project uses Java 8, Spring Boot 2.0 and is built with Maven. 

```
mvn clean verify
```
