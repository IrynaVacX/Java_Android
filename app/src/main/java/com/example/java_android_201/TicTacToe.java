package com.example.java_android_201;

import java.util.HashMap;

public class TicTacToe {
    int boardSize = 3;
    private int[][] gameBoard = new int[boardSize][boardSize];
    private HashMap<PlayerType, Integer> mappingToInt;
    private HashMap<Integer, PlayerType> mappingToType;
    private PlayerType currentPlayerType;

    public int getBoardSize() {
        return boardSize;
    }

    public TicTacToe() {
        // add parameter to constructor
        currentPlayerType = PlayerType.Crosses;

        mappingToInt = new HashMap<>();
        mappingToInt.put(PlayerType.Crosses, 1);
        mappingToInt.put(PlayerType.Zeroes, 0);
        mappingToInt.put(PlayerType.Empty, -1);

        mappingToType = new HashMap<>();
        mappingToType.put(1, PlayerType.Crosses);
        mappingToType.put(0, PlayerType.Zeroes);
        mappingToType.put(-1, PlayerType.Empty);

        resetBoard();
    }

    public void resetBoard() {
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                // empty value
                gameBoard[i][j] = mappingToInt.get(PlayerType.Empty);
            }
        }
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public PlayerType getCurrentPlayerType() {
        return currentPlayerType;
    }

    public PlayerType setItem(PlayerType playerType, int y, int x) {
        if(x < 0 || x >= boardSize || y < 0 || y >= boardSize) {
            return null;
        }

        gameBoard[y][x] = mappingToInt.get(playerType);

        return getWinner();
    }

    public PlayerType getItem(int y, int x) {
        if(x < 0 || x >= boardSize || y < 0 || y >= boardSize) {
            return null;
        }

        return mappingToType.get(gameBoard[y][x]);
    }

    public PlayerType getWinner() {
        // check each row
        PlayerType winner = null;

        winner = checkDiagonalLTR();
        if(winner != null) return winner;

        winner = checkDiagonalRTL();
        if(winner != null) return winner;

        for (int i = 0; i < boardSize; i++) {
            winner = checkRow(i);
            if(winner != null) return winner;

            winner = checkColumn(i);
            if(winner != null) return winner;
        }

        return null;
    }

    public boolean isDraw() {
        int emptyCellsCount = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if(mappingToType.get(gameBoard[i][j]) == PlayerType.Empty) {
                    ++emptyCellsCount;
                }
            }
        }

        if(emptyCellsCount > 0) return false;

        return getWinner() == null;
    }

    public PlayerType checkRow(int row) {
        if(mappingToType.get(gameBoard[row][0]) == PlayerType.Empty) return null;

        PlayerType typeToCheck = mappingToType.get(gameBoard[row][0]);

        for(int i = 1; i < boardSize; i++) {
            if(typeToCheck != mappingToType.get(gameBoard[row][i])) {
                return null;
            }
        }

        return typeToCheck;
    }

    public PlayerType checkColumn(int column) {
        if(mappingToType.get(gameBoard[0][column]) == PlayerType.Empty) return null;

        PlayerType typeToCheck = mappingToType.get(gameBoard[0][column]);

        for(int i = 1; i < boardSize; i++) {
            if(typeToCheck != mappingToType.get(gameBoard[i][column])) {
                return null;
            }
        }

        return typeToCheck;
    }

    public PlayerType checkDiagonalLTR() {
        if(mappingToType.get(gameBoard[0][0]) == PlayerType.Empty) return null;

        PlayerType typeToCheck = mappingToType.get(gameBoard[0][0]);

        for(int i = 1; i < boardSize; i++) {
            if(typeToCheck != mappingToType.get(gameBoard[i][i])) {
                return null;
            }
        }

        return typeToCheck;
    }

    public PlayerType checkDiagonalRTL() {
        if(mappingToType.get(gameBoard[0][boardSize - 1]) == PlayerType.Empty) return null;

        PlayerType typeToCheck = mappingToType.get(gameBoard[0][boardSize - 1]);

        for(int i = 0; i < boardSize; i++) {
            if(typeToCheck != mappingToType.get(gameBoard[i][boardSize - i - 1])) {
                return null;
            }
        }

        return typeToCheck;
    }

    public PlayerType getType(int boardValue) {
        return boardValue == 1 ? PlayerType.Crosses : boardValue == 0 ? PlayerType.Zeroes : PlayerType.Empty;
    }
}
