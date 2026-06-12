# 🔥 Symulacja Ewakuacji z Pożaru

Symulator ewakuacji oparty na automatach komórkowych, napisany w języku Java. Projekt realizuje architekturę wielomodułową (Silnik + GUI), rozdzielając warstwę obliczeniową od warstwy prezentacji zgodnie ze wzorcem MVC.   
Projekt wykorzystuje system budowania **Gradle**. Nie trzeba instalować żadnych dodatkowych bibliotek, skrypt sam pobierze zależności (w tym JavaFX). 

## 🚀 Główne funkcjonalności
* **Złożona separacja modułów:** Niezależny moduł `engine` (logika, agenci, fizyka pożaru) oraz moduł `gui` (widok JavaFX).
* **Interaktywny panel sterowania:** Szybka zmiana parametrów symulacji (rozmiar planszy, liczba cywili, strażaków, wyjść ewakuacyjnych oraz szybkość ticków symulacji).
* **Automatyczne raportowanie:** Po zakończeniu symulacji silnik generuje plik analityczny z danymi (liczba uratowanych, liczba ofiar, wydajność poszczególnych strażaków oraz przepustowość wyjść ewakuacyjnych).
* **Dynamiczny rendering:** Płynne skalowanie siatki i wyświetlanie stanu agentów na żywo.

---

## ⚙️ Quick Start

**Wymagania:** Java w wersji 17 lub nowszej.

1. Sklonuj repozytorium na swój dysk, wykonując w terminalu komendę:
   ```bash
   git clone https://github.com/Charl3s1337/Evacuation-Simulation.git
   ```
2. Otwórz terminal (konsolę) w głównym katalogu projektu.
3. Uruchom symulację w trybie graficznym za pomocą komendy:

   **Windows:**
   ```bash
   .\gradlew :gui:run
   ```
   **Linux / macOS:**
   ```bash
   ./gradlew :gui:run
   ```
---

## 🎬 Przykładowy przebieg symulacji

Po poprawnym uruchomieniu aplikacji otworzy się główne okno programu. Oto jak wygląda standardowy scenariusz użytkowy:

1. **Inicjalizacja:** Zobaczysz wygenerowaną planszę, na której losowo rozmieszczeni są Cywile (ikona czarnego "ludzika"), Strażacy (ikona strażaka), Ściany/Przeszkody (ikona ceglanego muru) oraz zarzewia Pożaru (czerwone płomienie). Zielone pola na krawędziach oznaczają Wyjścia Ewakuacyjne.
2. **Konfiguracja:** Na prawym panelu użyj suwaków, aby ustawić np. planszę 20x20, 30 cywili, 5 strażaków i 3 ogniska pożaru. Następnie kliknij **"Generuj nową planszę"**.
3. **Start Symulacji:** Kliknij zielony przycisk **"Start"**. Silnik zacznie przeliczać ticki (kroki). Możesz dynamicznie zmieniać suwak "Szybkość Symulacji" w trakcie jej trwania, aby przyspieszyć lub zwolnić zmiany obrazu.
4. **Obserwacja:** Cywile zaczną przemieszczać się po planszy, pożar będzie trawił puste komórki i zabijał cywili z nim sąsiadujących, a strażacy zajmą się gaszeniem kafelków ognia. Lewy panel "Raport Bieżący" na żywo zaktualizuje liczniki zgonów i uratowanych.
5. **Koniec i Raport:** Symulacja zatrzyma się automatycznie z dwóch powodów:
   * Strażacy ugaszą wszystkie pożary (Koniec zagrożenia).
   * Na planszy nie zostanie żaden żywy cywil (Wszyscy uratowani lub martwi).
6. **Eksport danych:** Po zatrzymaniu programu w **podkatalogu gui** głównego katalogu projektu, widoczny będzie automatycznie wygenerowany plik raportu z pełnym podsumowaniem akcji ratunkowej.

---

## 🏗️ Możliwości Rozwoju
Projekt stanowi fundament symulacyjny gotowy na dalszą rozbudowę w kilku kierunkach:
* **Inteligencja i psychologia tłumu:**
  * Implementacja logiki celowości ruchu agentów, pozwalająca na bezpośrednie kierowanie się do celu.
  * Wprowadzenie współczynnika stresu cywilów – zachowanie agentów mogłoby stawać się nielogiczne i paniczne w zależności od bliskości ognia.
  * Mechanika tratowania się tłumu, w której spanikowani cywile wchodziliby na innych, stając się przyczyną dodatkowych zgonów.
* **Zarządzanie jednostkami ratowniczymi:**
  * Implementacja centralnego systemu decyzyjnego dla strażaków, który analizowałby planszę i wysyłał jednostki w kierunku największych skupisk ognia.
* **Zaawansowana fizyka środowiska:**
  * Rozszerzenie modelu pożaru o stan zadymienia i śmierć w wyniku uduszenia.
  * Wprowadzenie rozróżnienia palności materiałów, co skutkowałoby różnymi efektami trawienia przestrzeni przez ogień.
* **Narzędzia edytorskie:**
  * Opracowanie trybu ręcznego tworzenia planszy, umożliwiającego symulowanie pożarów w odwzorowanych, istniejących obiektach (np. konkretnej sali wykładowej).