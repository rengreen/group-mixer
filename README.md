# Mikser Grup

Aplikacja webowa, która pozwala podzielić listę osób na grupy. Osoby mają różny poziom zaawansowania. Poziom otrzymanych grup powinien być możliwie wyrównany.

Aplikacja ma jedną funkcję - generowanie grup. Może być przydatna do transmisji on-line losowania grup, aby wszystkie zainteresowane osoby mogły uczestniczyć w losowaniu.

Inspiracją do powstania projektu był podział 20 osób uczących się Javy na 4 zespoły https://www.facebook.com/groups/350299415891965/.

## Algorytm

**Założenie:**  
Algorytm powinien działać dla dowolnej liczby osób o różnych poziomach zaawansowania i losowo dzielić uczestników na grupy o jak najbardziej wyrównanym poziomie.

**Dane wejściowe:**

- lista poziomów zaawansowania, z przypisanym `numerem poziomu` i jego `wartością punktową`
- lista osób z określonym poziomem zaawansowania
- lista docelowych grup (pustych)

    [zobacz szczegóły danych wejściowych >](##Dane-wejściowe)

**Realizacja:**

1. Dla każdego poziomu utwórz listę osób znajdujących się na tym poziomie zaawansowania. Pomieszaj losowo osoby wewnątrz każdego poziomu.
2. Ułóż wszystkie osoby w jedną `listę bazową`, malejąco według `numerów poziomów`. W przypadku osób o równym `numerze poziomu` zachowaj losową kolejność z punktu 1.
3. Pierwsza kolejka 
    - wylosuj `kolejność grup`, 
    - do pierwszej grupy w `kolejności grup` przypisz osobę z pierwszej pozycji na `liście bazowej`, do następnych grup w `kolejności grup` przypisuj następne wolne osoby z `listy bazowej`,
    - dla każdej grupy policz aktualną `sumę punktów` osób do niej należacych.
4. Druga kolejka
   - tak jak powyżej, wylosuj `kolejność grup`,
   - posortuj grupy rosnąco, według `sumy punktów`, zachowując wylosowaną `kolejność grup` w przypadku równej `sumy punktów` (aby jako pierwsza pobierała kolejną osobę z `listy bazowej` grupa aktualnie "najsłabsza"),
    - tak jak powyżej, do pierwszej grupy w `kolejności grup` przypisz osobę z pierwszej pozycji na `liście bazowej`, do następnych grup w `kolejności grup` przypisuj następne wolne osoby z `listy bazowej`,
    - tak jak powyżej, dla każdej grupy policz aktualną `sumę punktów` osób do niej należacych.
5. Powtarzaj drugą kolejkę, aż do pobrania wszystkich osób z `listy bazowej`.

**Dane wyjściowe:**
- dla każdej grupy lista wylosowanych osób
- dla każdej grupy `suma punktów` wszystkich osób w grupie

## Aplikacja

Aplikacja jest dostępna online https://group-mixer-r.herokuapp.com/  

Uruchomienie jako projekt Maven:  
Wpisz w IDE:  
`git clone https://github.com/rengreen/group-mixer.git`  
lub jeśli używasz SSH:  
`git clone git@github.com:rengreen/group-mixer.git`

## Dane wejściowe
Dane wejściowe są pobierane z plików `json`. Pliki z danymi znajdują się katalogu `src/main/resources/static/json/`. _W kolejnej wersji planowane jest wprowadzanie i edycja danych w GUI._
 
 Aby podać swoje dane zmodyfikuj pliki wejściowe. Obecnie aplikacja działa dla prawidłowych danych wejściowych. _Szczegółowe komunikaty informujące o podaniu nieprawidłowych danych wejściowych pojawią się w kolejnej wersji._


**Informacje o plikach wejściowych:**
    
**Poziomy:** plik `src/main/resources/static/json/levels.json`

```json
[
  {
    "value": 1,
    "name": "1",
    "weight": 1
  },
  {
    "value": 2,
    "name": "Juniorki",
    "weight": 6
  }
]
```

_value_ - unikalna liczba, określająca kolejność poziomów,  
_name_ - dowolna nazwa (wyświetlana na ekranie),  
_weight_ - liczba punktów, czyli waga poziomu - celem algorytmu jest stworzenie grup o równej lub podobnej liczbie punktów; wartość _weight_ nie musi być unikalna.

**Osoby:** `src/main/resources/static/json/persons.json`  
przykładowy plik:

```json
[
  {
    "name": "Anna Nowak",
    "level": 1
  },
  {
    "name": "osoba 2",
    "level": 2
  }
]
```

_name_ - dowolna nazwa (wyświetlana na ekranie),  
_level_ - liczba określająca poziom, zdefiniowana w polu _value_ w pliku. `levels.json`.

**Grupy:** `src/main/resources/static/json/teams.json`  
przykładowy plik:

```json
[
  {
    "name": "Gamerki"
  },
  {
    "name": "Heroski"
  }
]
```

_name_ - dowolna nazwa (wyświetlana na ekranie).