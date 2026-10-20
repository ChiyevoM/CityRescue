# CityRescue

A deterministic emergency response simulation built with Java 17 and Maven. CityRescue models a city grid, emergency stations, response units, and incidents, simulating how emergency services are dispatched and coordinated as time progresses.

## Overview

CityRescue is a Java coursework project focused on object-oriented programming, simulation logic, and deterministic system behaviour. The system represents the backend of an emergency dispatch platform, managing emergency resources and tracking incidents from reporting through to resolution.

The simulation is designed to produce consistent results for the same sequence of inputs, following predefined rules for dispatching, movement, and incident processing.

## Features

- **City grid:** A two-dimensional map with configurable dimensions and blocked cells.
- **Emergency stations:** Stations with fixed grid locations and configurable unit capacities.
- **Emergency response units:** Ambulances, fire engines, and police cars, modelled using inheritance and polymorphism.
- **Incident management:** Create, cancel, and escalate incidents with defined severity levels and lifecycle statuses.
- **Deterministic dispatching:** Assign eligible units according to Manhattan distance and predefined tie-breaking rules.
- **Movement simulation:** Move units across the grid using a fixed directional priority while respecting obstacles.
- **Time-based simulation:** Advance the simulation through ticks, tracking arrivals, on-scene work, and incident resolution.
- **State reporting:** Generate consistently formatted status reports for units, incidents, stations, and the simulation.

## Technologies

- Java 17
- Maven
- JUnit 5
- Object-oriented programming
- Arrays and enumerations

## Project Structure

```text
CityRescue/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── [project packages]/
│   │           ├── CityRescue.java
│   │           ├── CityRescueImpl.java
│   │           ├── CityMap.java
│   │           ├── Station.java
│   │           ├── Incident.java
│   │           ├── Unit.java
│   │           ├── Ambulance.java
│   │           ├── FireEngine.java
│   │           ├── PoliceCar.java
│   │           ├── enums/
│   │           └── exceptions/
│   └── test/
│       └── java/
└── README.md
```

*Note: The structure above illustrates the intended design. Actual package names and file locations may differ.*

## System Design

The system is organised around a set of cooperating classes:

| Class | Responsibility |
|---|---|
| `CityRescueImpl` | Implements the public interface and coordinates the simulation. |
| `CityMap` | Manages grid dimensions, obstacles, and legal movement. |
| `Station` | Stores station information, unit ownership, and capacity. |
| `Incident` | Stores incident details, severity, status, and unit assignment. |
| `Unit` | Abstract base class defining shared emergency unit behaviour. |
| `Ambulance` | Handles medical incidents. |
| `FireEngine` | Handles fire incidents. |
| `PoliceCar` | Handles crime incidents. |

Enumerations represent incident types, unit types, incident statuses, and unit statuses.

## Simulation Rules

### Dispatching

Reported incidents are considered in ascending incident ID order. For each incident, the system selects an eligible unit using the following priorities:

1. Shortest Manhattan distance to the incident.
2. Lowest unit ID when distances are tied.
3. Lowest home station ID when the preceding criteria are tied.

Units must be available and capable of handling the relevant incident type.

### Movement

Units travelling to incidents move one cell per simulation tick. Candidate directions are evaluated in the order **North, East, South, West**. The first legal move that reduces Manhattan distance is preferred; if none does, the first legal move is selected.

Movement respects grid boundaries and blocked cells without using pathfinding algorithms.

### Incident Lifecycle

Incidents follow a defined lifecycle:

`REPORTED → DISPATCHED → IN_PROGRESS → RESOLVED`

Eligible incidents may also be cancelled before completion. Incident severity can be escalated while the incident remains active.

## Getting Started

### Prerequisites

- JDK 17
- Apache Maven
- Git

### Clone the Repository

```bash
git clone <repository-url>
cd CityRescue
```

Replace `<repository-url>` with your GitHub repository's clone URL.

### Compile the Project

```bash
mvn clean compile
```

### Run the Tests

```bash
mvn clean test
```

The project uses Maven to manage dependencies and JUnit 5 for testing.

## Design Principles

The implementation is guided by the following principles:

- **Encapsulation:** Each class manages its own relevant state and behaviour.
- **Inheritance and polymorphism:** Emergency unit subclasses implement unit-specific behaviour through a shared abstract class.
- **Determinism:** Explicit ordering and tie-breaking rules ensure predictable simulation outcomes.
- **Design by contract:** Public methods validate inputs and enforce the specified exceptions and state-transition rules.
- **Array-based storage:** Core entities are managed using arrays in accordance with the coursework requirements.

## Testing

Testing focuses on correct system behaviour, including:

- Station capacity and unit management.
- Incident creation, cancellation, and escalation.
- Dispatch selection and tie-breaking.
- Movement around obstacles and grid boundaries.
- Tick sequencing and incident resolution.
- Exception handling and preservation of state after failed operations.
- Exact output formatting and deterministic ordering.

## Project Status

Academic coursework project. Implementation and testing are in progress.

## Learning Objectives

This project provides practical experience with Java class design, abstract classes, inheritance, polymorphism, enumerations, arrays, exception handling, and deterministic simulation logic.
