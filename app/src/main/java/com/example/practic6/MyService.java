package com.example.practic6;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
public class MyService extends Service {
    private WindowManager windowManager;
    private View overlayView;
    private Button button;
    private TextView infoTextView;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        overlayView = LayoutInflater.from(this).inflate(R.layout.service_layout, null);
        button = overlayView.findViewById(R.id.button3);
        infoTextView = overlayView.findViewById(R.id.banner);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER;
        windowManager.addView(overlayView, params);
        button.setOnClickListener(view -> {
            windowManager.removeView(overlayView);
            stopSelf();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        infoTextView.setText("Вы не забыли отдать кота?");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(overlayView);
    }
}
