# SGR Backend Implementation in Spring Boot

## Quick intro to SGR
SGR is the new system for recycling PET bottles implemented in Romania, similar to other countries in the EU. It works by adding a deposit to the price of PET containers, which could be cashed out once the container is returned to one of the selected locations (usually in stores).

## Libraries used

- Spring Boot
- JPA
- Hibernate

## Basic configuration

It is necessary to create a configuration file (you can also use a CLI configuration if you prefere, more info available at the [Spring documentation](https://docs.spring.io/spring-boot/docs/1.4.1.RELEASE/reference/html/boot-features-external-config.html))

In the usual approach, the file is found under `src/main/resources/application.properties` and follows the [general Spring configuration structure](https://docs.spring.io/spring-boot/docs/1.4.1.RELEASE/reference/html/boot-features-external-config.html#boot-features-external-config-profile-specific-properties)

For a quick start, only the following properties are necessary:

`spring.application.name`

`spring.jpa.generate-ddl`

`spring.jpa.show-sql`

`spring.datasource.driver-class-name`

`spring.datasource.url`

`spring.datasource.username`

`spring.datasource.password`

## System architecture

### Definitions
- `SGRItem` is a simple product that takes part in the SGR system
- `SGRStore` is a location that can `ISSUE` products or `CONSUME` (or `SCAN`) products.
- `SGRPretGarantie` is a price for the return issued at a certain point in time. The latest one is always valid, but the system keeps track of past ones
- `SGRPurchase` represents an `ISSUE` transaction (i.e. a product is bought from a `SGRStore`)

### Transaction types
- `ISSUE` represents a purchase of an SGR item
- `CONSUME` (or `SCAN`) represents the return of a previously purchased SGR item

### Workflow

Item flow:
- `SGRItem` gets created
- `SGRStore A` executes an `ISSUE` transaction and creates a `SGRPurchase` for the item
- `SGRStore B` executes a `CONSUME` transaction and the `SGRPurchase` is marked as `SGRComplete`

*Note: Store A and B can be the same store, or completely different ones*