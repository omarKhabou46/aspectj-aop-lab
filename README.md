# Aspect-Oriented Programming (AOP) with AspectJ & Spring AOP

This repository contains two educational Java projects demonstrating **Aspect-Oriented Programming (AOP)** concepts—such as cross-cutting concerns (logging, security, and business rule patching)—using both **Compile-Time Weaving with AspectJ** and **Runtime Proxy-Based AOP with Spring AOP**.

---

## 📂 Repository Structure

```text
aspectj-aop/
├── BankingAccount-AOP/      # Pure AspectJ project (Compile-Time Weaving)
│   ├── pom.xml
│   └── src/main/java/com/enset/
│       ├── Main.java
│       ├── aspects/
│       │   ├── LoggingAspect.java
│       │   ├── PatchAspect.java
│       │   └── SecurityAspect.java
│       ├── metier/
│       │   ├── IMetier.java
│       │   └── MetierImpl.java
│       └── models/
│           └── Compte.java
└── spring-aop/              # Spring Framework AOP project (Proxy-based)
    ├── pom.xml
    └── src/main/java/com/enset/
        ├── Main.java
        ├── annotations/
        │   └── Log.java
        ├── aspects/
        │   └── LogAspects.java
        └── metier/
            ├── IMetier.java
            └── MetierImpl.java
```

---

## 🛠️ Prerequisites

- **Java Development Kit (JDK):** 21
- **Build Tool:** Apache Maven 3.8+

---

## 📦 Projects Overview

### 1. `BankingAccount-AOP` (AspectJ Compile-Time Weaving)

A console-based bank account management application that uses **AspectJ** (`aspectjrt` and `aspectj-maven-plugin`) to weave aspects directly into bytecode at compile time.

#### Core Domain & Business Logic
- **`Compte`**: Model representing a bank account (`code`, `balance`).
- **`IMetier` / `MetierImpl`**: Service layer managing accounts in memory with operations to add an account (`addAccount`), deposit (`verser`), withdraw (`retirer`), and fetch an account (`getCompte`).

#### Implemented Aspects
1. **`SecurityAspect`**:
   - **Pointcut**: `execution(* com.enset.Main.start(..))`
   - **Advice**: `@Around` — Intercepts application startup and prompts for a username and password (`root` / `password`) before allowing execution to proceed.
2. **`LoggingAspect`**:
   - **Pointcut**: `execution(* com.enset.metier.MetierImpl.*(..))`
   - **Advice**: `@Around` — Logs method entry, exit, and execution duration (in milliseconds) to `log.xml` for every method called on `MetierImpl`.
3. **`PatchAspect`**:
   - **Pointcut**: `execution(* com.enset.metier.MetierImpl.retirer(..)) && args(code, montant)`
   - **Advice**: `@Around` — Validates whether the account balance is strictly greater than the withdrawal amount (`compte.getBalance() > montant`) before proceeding with `retirer`, preventing overdrafts without modifying `MetierImpl`.

#### Build & Run
```bash
cd BankingAccount-AOP
mvn clean compile exec:java -Dexec.mainClass="com.enset.Main"
```
*(Default credentials when prompted: username `root`, password `password`. Operations: `v` for deposit, `r` for withdrawal.)*

---

### 2. `spring-aop` (Spring AOP & Custom Annotations)

A Spring application demonstrating **proxy-based runtime AOP** using `@EnableAspectJAutoProxy` and custom annotation pointcuts.

#### Core Components
- **`@Log`**: Custom annotation (`com.enset.annotations.Log`) used to mark methods that should be intercepted.
- **`IMetier` / `MetierImpl`**: Spring `@Service` bean defining two methods:
  - `process()`: Standard method (not logged).
  - `compute()`: Annotated with `@Log`, returning a computed value.
- **`LogAspects`**:
  - **Pointcut**: `@Around("@annotation(com.enset.annotations.Log)")`
  - **Advice**: Logs before and after execution of any method annotated with `@Log` and calculates the total execution time.

#### Build & Run
```bash
cd spring-aop
mvn clean compile exec:java -Dexec.mainClass="com.enset.Main"
```

---

## 🔍 Key Concepts Demonstrated

| Feature | `BankingAccount-AOP` (AspectJ) | `spring-aop` (Spring AOP) |
| :--- | :--- | :--- |
| **Weaving Mechanism** | Compile-time bytecode weaving (`ajc`) | Runtime dynamic proxies (JDK Proxy / CGLIB) |
| **IoC Container Required** | No | Yes (Spring `ApplicationContext`) |
| **Can Intercept `private` / `static` Methods** | Yes (e.g., `Main.start()`) | No (Spring bean public/protected methods only) |
| **Pointcut Styles Used** | `execution(...)`, `args(...)` | `@annotation(...)` |
