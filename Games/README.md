# 🎮 Java Projekt – Kolekce tří her

Tento projekt obsahuje tři jednoduché hry vytvořené v Javě pomocí knihovny Swing:

- 🐍 Snake
- ✊✋✌️ Rock–Paper–Scissors (Kámen–Nůžky–Papír)
- 🍪 Cookie Clicker

## 🧰 Technologie
- Java 17+
- Swing (GUI)
- Maven (build system)

## 🚀 Spuštění projektu

### IntelliJ IDEA (doporučeno):
1. Otevři složku `Games/` jako Maven projekt.
2. Najdi soubor `Main.java` nebo `MainMenu.java` ve složce `src/main/java/org/`.
3. Spusť program – zobrazí se grafické menu pro výběr hry.

### Nebo pomocí příkazové řádky:
```bash
cd Games
mvn compile
mvn exec:java -Dexec.mainClass="org.MainMenu"
```

*(Předpokládá, že máš nainstalovaný Maven a Java SDK.)*

## 💾 Poznámka
- Skóre pro Cookie Clicker se ukládá do souboru `cookie_score.txt` ve stejné složce.
- Projekt byl vytvořen jako zápočtový úkol v rámci 2. semestru předmětu Programování v Javě.

## 👤 Autor
Student: *[Tvoje jméno]*  
Škola: *[Název školy]*  
Rok: 2025
