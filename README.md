# 🃏 BlackJack Java Game

## 🎯 Project Goal
This project is both a **learning exercise** in Java programming and a **fully playable desktop game** inspired by the classic **Blackjack (21)** card game.  
It was created to improve object-oriented design skills, GUI development, and game logic implementation using Java Swing.

---

## ⚙️ Tech Stack
- **Language:** Java 17  
- **Framework:** Swing (2D graphical interface)  
- **Build Tool:** Maven  
- **External Libraries:** None — all components implemented from scratch  

---

## 🚀 How to Run

### Requirements
- **Java 17** or higher installed  
- **Maven** (optional, for building the JAR)

### Option 1 — Run from IDE
1. Clone the repository:  
   ```bash
   git clone https://github.com/NikitaSipeykin/BlackJackJava.git
   ```
2. Open the project in **IntelliJ IDEA** or another IDE.
3. Run the main class (e.g., `App.java`).

### Option 2 — Build and Run via Maven
1. Build the JAR file:
   ```bash
   mvn clean package
   ```
2. Run the JAR:
   ```bash
   java -jar target/BlackJackJava.jar
   ```

---

## 🧩 Project Structure / Game Logic

The game is organized into several core classes:

| Class | Description |
|-------|--------------|
| `Card` | Represents a single playing card with suit and rank. |
| `GamePanel` | Contains the basic logic of the game. |
| `User` | Represents a player or the dealer, including their hand and score calculation. |
| `App` | Entry point of the application, initializes the game window and starts the main loop. |

### Gameplay
- The player starts with two cards, the dealer also receives two (one hidden).  
- The player may **Hit** (draw another card) or **Stand** (end their turn).  
- The goal is to get as close as possible to **21** without going over.  
- After the player stands, the dealer plays automatically.  
- The winner is determined based on standard Blackjack rules.
