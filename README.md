🧩 Eight Puzzle Game

The Eight Puzzle Game is a classic sliding puzzle implemented in Java with a JavaFX UI.
The game can be played in two modes:

🎮 Manual Mode – where the user can solve the puzzle interactively.

🤖 AI Mode – where the game automatically solves the puzzle using the A* (A-star) Search Algorithm.

🚀 Features

Interactive JavaFX GUI for smooth gameplay.

Manual Play: Move tiles using the UI until you reach the goal state.

AI Solver: Automatically solves the puzzle using A* with heuristics.

Detects solvable vs. unsolvable configurations.

Step-by-step solution visualization.

🛠️ Tech Stack

Language: Java

UI: JavaFX

Algorithm: A* Search Algorithm (with heuristics)

📂 Project Structure
EightPuzzleGame/
│── src/
│   ├── Main.java           # Entry point
│   ├── Board.java          # Puzzle board logic
│   ├── AStarSolver.java    # A* search algorithm implementation
│   ├── GameController.java # JavaFX controller for UI interactions
│   └── Utils.java          # Helper functions
│── resources/              # FXML layouts, CSS, images
│── README.md               # Project documentation

⚙️ Installation & Setup

Clone the Repository

git clone https://github.com/your-username/EightPuzzleGame.git
cd EightPuzzleGame


Open in an IDE (IntelliJ IDEA, Eclipse, or VS Code with JavaFX support).

Run the Application

Run Main.java

Make sure JavaFX SDK is properly configured in your IDE.

🎮 How to Play

Manual Mode:

Click on the tiles to slide them into the blank space.

Rearrange until the numbers are in order from 1–8 with the blank at the bottom-right.

AI Mode (A*):

Click Solve with AI button.

The puzzle will be solved automatically step by step using the A* search algorithm.

📸 Screenshots



✨ Eight Puzzle Game – Combining Classic Puzzle Fun with AI Power!
