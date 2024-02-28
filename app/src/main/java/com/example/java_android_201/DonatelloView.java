package com.example.java_android_201;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DonatelloView extends View {

    public DonatelloView(Context context) {
        super(context);
    }

    public DonatelloView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DonatelloView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        Paint greenPaint = new Paint();
        greenPaint.setStyle(Paint.Style.FILL);

        Paint yellowPaint = new Paint();
        yellowPaint.setColor(Color.parseColor("#DBB93D"));
        yellowPaint.setStyle(Paint.Style.FILL);

        Paint magentaPaint = new Paint();
        magentaPaint.setColor(Color.parseColor("#733FBA"));
        magentaPaint.setStyle(Paint.Style.FILL);

        Paint brownPaint = new Paint();
        brownPaint.setColor(Color.parseColor("#433B3A"));
        brownPaint.setStyle(Paint.Style.FILL);

        // Панцирь
        RectF bodyRect = new RectF(width / 5.5f, height / 3.5f,
                width  - width / 5.5f, height - height / 3.5f);
        greenPaint.setColor(Color.parseColor("#346D24"));   //dark-green
        canvas.drawOval(bodyRect, greenPaint);

        // Тело
        bodyRect = new RectF(width / 4.7f, height / 3.2f,
                width  - width / 4.7f, height - height / 3.2f);
        greenPaint.setColor(Color.parseColor("#41B646"));   //green
        canvas.drawOval(bodyRect, greenPaint);

        // Пузо
        bodyRect = new RectF(width / 3f, height / 2.7f,
                width  - width / 3f, height / 2.7f + 600);
        canvas.drawRect(bodyRect, yellowPaint);
        canvas.drawCircle(width / 2, height / 2.7f + 600, Math.min(width, height) / 5.97f, yellowPaint);

        // Руки
        canvas.drawCircle(width / 5, height / 2f + 210, Math.min(width, height) / 13f, greenPaint);
        canvas.drawCircle(width - width / 5, height / 2f + 210, Math.min(width, height) / 13f, greenPaint);

        // Ноги
        bodyRect = new RectF(width / 3f, height / 2f + 450,
                width / 3f + 150, height / 2f + 850);
        canvas.drawRect(bodyRect, greenPaint);
        bodyRect = new RectF(width - width / 3f, height / 2f + 450,
                width - width / 3f - 150, height / 2f + 850);
        canvas.drawRect(bodyRect, greenPaint);

        // Ленты на ноги
        bodyRect = new RectF(width / 3f - 5, height / 2f + 720,
                width / 3f + 155, height / 2f + 800);
        canvas.drawRect(bodyRect, magentaPaint);
        bodyRect = new RectF(width - width / 3f + 5, height / 2f + 720,
                width - width / 3f - 155, height / 2f + 800);
        canvas.drawRect(bodyRect, magentaPaint);

        // Голова
        bodyRect = new RectF(width / 3.2f, height / 4f,
                width  - width / 3.2f, height / 4f + 300);
        canvas.drawOval(bodyRect, greenPaint);
        canvas.drawCircle(width / 2, height / 4f, Math.min(width, height) / 7, greenPaint);

        // Лента на голову
        bodyRect = new RectF(width / 2.9f, height / 4.9f,
                width  - width / 2.9f, height / 4.9f + 150);
        canvas.drawRect(bodyRect, magentaPaint);

        // Глаза
        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2 - 40, height / 3.5f - 40, 20, whitePaint);
        canvas.drawCircle(width / 2 + 40, height / 3.5f - 40, 20, whitePaint);

        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2 - 40, height / 3.5f - 40, 10, blackPaint);
        canvas.drawCircle(width / 2 + 40, height / 3.5f - 40, 10, blackPaint);

        // Рот
        Path mouthPath = new Path();
        mouthPath.moveTo(width / 2 - 50, height / 3.4f - 20);
        mouthPath.quadTo(width / 2, height / 3.4f + 20, width / 2 + 50, height / 3.4f - 20);
        canvas.drawPath(mouthPath, blackPaint);

        // Палка
        bodyRect = new RectF(width - width / 7f, height / 4.5f,
                width - width / 7f - 85, height - height / 4.5f);
        canvas.drawRect(bodyRect, brownPaint);
        bodyRect = new RectF(width - width / 7f, height / 2.5f + 100,
                width - width / 7f - 85, height - height / 2.5f + 100 );
        magentaPaint.setColor(Color.parseColor("#9C82E6"));
        canvas.drawRect(bodyRect, magentaPaint);
    }
}
