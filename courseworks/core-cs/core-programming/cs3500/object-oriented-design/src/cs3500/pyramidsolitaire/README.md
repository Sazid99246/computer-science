Assignment 3 - Project Adjustments:
* Adjusted testing structure to resolve unique card validation checks within custom test decks.
* Integrated the PyramidSolitaireView interface directly inside PyramidSolitaireTextualView.

# Assignment 4: Playing with Cards, Part 3

## Architectural Changes & Refactoring Strategy
To support multiple variations of Pyramid Solitaire without duplicating core game logic, the design was refactored using an abstract hierarchy:

1. **`AbstractPyramidSolitaire`**: Added under the package `cs3500.pyramidsolitaire.model.hw04`. This class encapsulates all standard model states (`pyramid`, `stock`, `drawPile`, `gameStarted`, `numRows`, `numDraw`), validation methods (`checkGameStarted`, `isValidCoordinates`, `validateDeck`), and rule-invariant operations like single-card removal of Kings, draw mutations, and boilerplate getters (`getScore`, `getCardAt`, `getDrawCards`).
2. **`BasicPyramidSolitaire`**: Refactored to extend `AbstractPyramidSolitaire` while maintaining its exact public signature under `cs3500.pyramidsolitaire.model.hw02`. This preserved complete backward compatibility with older assignment suites and avoided breaks in the grading environment.
3. **`RelaxedPyramidSolitaire`**: Derived directly from `BasicPyramidSolitaire`, overriding pairing logic to implement the relaxed rule validation (`isRelaxedExposed`) and game-over assessments gracefully.
4. **`TriPeaksPyramidSolitaire`**: Derived from `AbstractPyramidSolitaire`, implementing custom row width math, a double deck structure (104 cards), and a unique grid construction strategy during `startGame` to form 3 distinct peaks.

## Assumptions & Extensions
* For `TriPeaksPyramidSolitaire`, row widths expand out non-linearly on rows preceding the overlap point due to inter-peak gaps. Empty spaces in the structure are padded with `null` so that the standard text-based view renders them with correct geometric columns without changing any view logic or exposing internal row configurations.