package com.example.java_android_201;

import android.Manifest;
import android.content.ContentResolver;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TicTacToe ticTacToe = new TicTacToe();
    private List<Button> gameButtons = null;
    private PlayerType currentPlayerType = PlayerType.Crosses;
    private static boolean gameStarted = false;
    private String colorZero = "#4B88AF";
    private String colorCross = "#B15050";
    private int winsCross = 0;
    private int winsZero = 0;
    private TextView scoreView;
    private TextView turnView;
    private Random random = new Random();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startGame();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_land);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        }

        initControls();
        drawBoard();
    }

    public void initControls() {
        int size = ticTacToe.getBoardSize();
        gameButtons = new ArrayList<>();
        gameButtons.add(findViewById(R.id.button1));
        gameButtons.add(findViewById(R.id.button2));
        gameButtons.add(findViewById(R.id.button3));
        gameButtons.add(findViewById(R.id.button4));
        gameButtons.add(findViewById(R.id.button5));
        gameButtons.add(findViewById(R.id.button6));
        gameButtons.add(findViewById(R.id.button7));
        gameButtons.add(findViewById(R.id.button8));
        gameButtons.add(findViewById(R.id.button9));

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                Button btn = gameButtons.get((i * size) + j);
                btn.setOnClickListener(this::onClick);
                GameButtonTag tag = new GameButtonTag();
                tag.setX(j);
                tag.setY(i);
                btn.setTag(tag);
            }
        }

        scoreView = findViewById(R.id.tv_score);
        turnView = findViewById(R.id.tv_turn);

        findViewById(R.id.button_newgame).setOnClickListener((view) -> startGame());
    }

    public void startGame() {
        ticTacToe.resetBoard();
        gameStarted = true;
        currentPlayerType = PlayerType.Crosses;

        initControls();
        this.drawBoard();
    }

    public void drawBoard() {
        int[][] board = ticTacToe.getGameBoard();
        int size = ticTacToe.getBoardSize();

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                PlayerType elem = ticTacToe.getType(board[i][j]);
                String buttonText = elem == PlayerType.Crosses ? "X" : elem == PlayerType.Zeroes ? "O" : "";
                String buttonTextColor = elem == PlayerType.Crosses ? colorCross : colorZero;
                Button btn = gameButtons.get((i * size) + j);
                btn.setText(buttonText);
                btn.setTextColor(Color.parseColor(buttonTextColor));
            }
        }

        String playerTypeStr = currentPlayerType == PlayerType.Crosses ? "X" : "O";
        scoreView.setText(String.format("%d:%d", winsCross, winsZero));
        turnView.setText(ticTacToe.isDraw() ? "Draw" : String.format("%s's turn", playerTypeStr));
    }

    public void onClick(View view) {
        System.out.println(gameStarted);
        if(!gameStarted) return;

        Button btn = (Button)view;
        GameButtonTag tag = (GameButtonTag)btn.getTag();

        if(ticTacToe.getItem(tag.getY(), tag.getX()) != PlayerType.Empty) {
            return;
        }

        PlayerType winner = ticTacToe.setItem(currentPlayerType, tag.getY(), tag.getX());

        if(currentPlayerType == PlayerType.Crosses) {
            if(winner != null) {
                winsCross++;
                gameStarted = false;
            }
            else {
                currentPlayerType = PlayerType.Zeroes;
            }
        }
        else {
            if(winner != null) {
                winsZero++;
                gameStarted = false;
            }
            else {
                currentPlayerType = PlayerType.Crosses;
            }
        }

        drawBoard();
    }

    public void botTurn() {
        if(!gameStarted) return;
        int size = ticTacToe.getBoardSize();

        for (int i = 0; i < size * size; i++) {
            int y = random.nextInt(size);
            int x = random.nextInt(size);

            if(ticTacToe.getItem(y, x) == PlayerType.Empty) {
                onClick(gameButtons.get((y * size) + x));
                break;
            }
        }
    }
}

class GameButtonTag {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
