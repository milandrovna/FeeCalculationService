# Delivery Fee Calculator

## Overview
This Spring Boot application calculates the delivery fee based on:
- **City**
- **Vehicle type**
- **Latest weather conditions** (if timestamp is provided weather conditions are
  obtained for a specified timestamp or the closest available record)

## Features
- RESTful API for fee calculation
- Integration with the latest weather data
- YAML-based API documentation

## Prerequisites
- Java 21 or higher
- Gradle 8.13
- Spring Boot 3.4.3
- Groovy: 3.0.22

## Installation and Setup

1. **Clone the repository:**
```bash
git clone https://github.com/milandrovna/FeeCalculationService.git
```
2. **Navigate to the project directory:**
```bash
cd FeeCalculationService
```
3. **Run the application:**
```bash
./gradlew bootRun
```
**Alternatively, if you prefer to run the main class directly from your IDE, open the project and run:**
```bash
src/main/java/com/fujitsu/trialtask/TrialtaskApplication.java
```
