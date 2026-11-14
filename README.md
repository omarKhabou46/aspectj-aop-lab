# Aspect-Oriented Programming (AOP) with AspectJ & Spring AOP

This repository contains two educational Java projects demonstrating **Aspect-Oriented Programming (AOP)** concepts—separating cross-cutting concerns such as **logging**, **authentication/authorization**, and **business rule validation (patching)** from core business logic—using both **Compile-Time Weaving with AspectJ** and **Runtime Proxy-Based AOP with Spring AOP**.

---

## 📂 Repository Structure

```text
aspectj-aop/
├── BankingAccount-AOP/                # Pure AspectJ project (Compile-Time Weaving)
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
└── spring-aop/                        # Spring Framework AOP project (Proxy-based)
    ├── pom.xml
    └── src/main/java/com/enset/
        ├── Main.java
        ├── annotations/
        │   ├── Log.java
        │   └── SecuredByAspect.java
        ├── aspects/
        │   ├── LogAspects.java
        │   └── SecuredAspect.java
        └── metier/
            ├── IMetier.java
            ├── MetierImpl.java
            └── SecurityContext.java
```

---

## 🛠️ Tech Stack & Prerequisites

- **Java Development Kit (JDK):** 21
- **Build Tool:** Apache Maven 3.8+
- **Libraries & Frameworks:**
  - **AspectJ:** `1.9.22` (`aspectjrt`, `aspectjtools`, `aspectj-maven-plugin 1.14`)
  - **Spring Framework:** `7.0.10` (`spring-core`, `spring-context`, `spring-beans`, `spring-aop`, `spring-aspects`)

---

## 📦 Projects Overview

### 1. `BankingAccount-AOP` (AspectJ Compile-Time Weaving)

A console-based bank account management application that uses **AspectJ** (`ajc` compiler via `aspectj-maven-plugin`) to weave aspects directly into Java bytecode at compile time, without requiring an IoC container.

#### Core Domain & Business Logic
- **`Compte`**: Domain model representing a bank account (`code`, `balance`).
- **`IMetier` / `MetierImpl`**: In-memory service layer providing operations to register an account (`addAccount`), deposit funds (`verser`), withdraw funds (`retirer`), and retrieve account details (`getCompte`).

#### Implemented Aspects
1. **`SecurityAspect`**:
   - **Pointcut**: `execution(* com.enset.Main.start(..))`
   - **Advice**: `@Around` — Intercepts the `private static` `Main.start()` method at startup and prompts for credentials (`root` / `password`) before allowing the application to run.
2. **`LoggingAspect`**:
   - **Pointcut**: `execution(* com.enset.metier.MetierImpl.*(..))`
   - **Advice**: `@Around` — Logs method entry, exit, and execution duration (in milliseconds) to `log.xml` using `java.util.logging.FileHandler` for every method invoked on `MetierImpl`.
3. **`PatchAspect`**:
   - **Pointcut**: `execution(* com.enset.metier.MetierImpl.retirer(..)) && args(code, montant)`
   - **Advice**: `@Around` — Inspects the target `MetierImpl` instance and checks that the account balance is strictly greater than the withdrawal amount (`compte.getBalance() > montant`) before proceeding with `retirer`, preventing overdrafts without modifying `MetierImpl`.

#### Build & Run
```bash
cd BankingAccount-AOP
mvn clean compile exec:java -Dexec.mainClass="com.enset.Main"
```
> **Interactive Prompts:**
> - **Default Credentials:** username `root`, password `password`
> - **Operations:** `v` to deposit (*verser*), `r` to withdraw (*retirer*)

---

### 2. `spring-aop` (Spring AOP & Custom Annotations)

A Spring application demonstrating **runtime proxy-based AOP** (`@EnableAspectJAutoProxy`) driven by custom annotations for method execution logging and role-based access control.

#### Core Components
- **Custom Annotations**:
  - **`@Log`**: Marks methods whose execution and duration should be logged.
  - **`@SecuredByAspect(roles = {...})`**: Declares the roles allowed to invoke a method.
- **`SecurityContext`**: Holds the authenticated user's credentials (`root` / `password`) and assigned roles in static context, exposing `authenticate(...)` and `hasRole(...)`.
- **`IMetier` / `MetierImpl`**: Spring `@Service` bean defining two business operations:
  - `process()`: Protected with `@SecuredByAspect(roles = {"USER", "ADMIN"})`.
  - `compute()`: Protected with `@SecuredByAspect(roles = {"ADMIN"})` and instrumented with `@Log`.

#### Implemented Aspects
1. **`LogAspects`**:
   - **Pointcut**: `@Around("@annotation(com.enset.annotations.Log)")`
   - **Advice**: Logs before and after method execution and outputs the elapsed time in milliseconds.
2. **`SecuredAspect`**:
   - **Pointcut**: `@Around(value = "@annotation(securedByAspect)", argNames = "pjp,securedByAspect")`
   - **Advice**: Reads the required roles from `@SecuredByAspect` and verifies them against `SecurityContext.hasRole(...)`. Proceeds if authorized, or throws `RuntimeException("unauthorized >>> 403")` otherwise.

#### Execution Flow in `Main`
1. Authenticates the user `root` with role `{"USER"}`.
2. Initializes the Spring `AnnotationConfigApplicationContext` and retrieves the `IMetier` proxy bean.
3. Calls `metier.process()` → **Succeeds** (requires `USER` or `ADMIN`).
4. Calls `metier.compute()` → **Throws `unauthorized >>> 403`** (requires `ADMIN`).

#### Build & Run
```bash
cd spring-aop
mvn clean compile exec:java -Dexec.mainClass="com.enset.Main"
```

---

## 🔍 Key Concepts Demonstrated

| Feature | `BankingAccount-AOP` (AspectJ) | `spring-aop` (Spring AOP) |
| :--- | :--- | :--- |
| **Weaving Mechanism** | Compile-time bytecode weaving (`ajc`) | Runtime dynamic proxies (JDK Dynamic Proxy / CGLIB) |
| **IoC Container Required** | No | Yes (Spring `ApplicationContext`) |
| **Can Intercept `private` / `static` Methods** | Yes (e.g., `private static Main.start()`) | No (Spring bean non-private methods only) |
| **Pointcut Designators Used** | `execution(...)`, `args(...)` | `@annotation(...)` |
| **Cross-Cutting Concerns** | Authentication, XML File Logging, Overdraft Patching | Role-Based Authorization (`@SecuredByAspect`), Execution Timing (`@Log`) |
