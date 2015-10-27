package com.ar.minesweeper.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by ariviere on 25/10/15.
 * Listen to a shake
 * based on http://jasonmcreynolds.com/?p=388
 */
public class ShakingDetector implements SensorEventListener {

    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    private static final int SHAKE_SLOP_TIME_MS = 500;

    private OnShakeListener mListener;
    private long mShakeTimestamp;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (mListener != null) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement.
            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();
                // ignore shake events too close to each other (500ms)
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }

                mShakeTimestamp = now;

                mListener.onShake();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    /**
     * listener for the shaking
     * @param listener listener
     */
    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    /**
     * callback listener
     */
    public interface OnShakeListener {
        /**
         * triggered when the phone is shaked
         */
        void onShake();
    }

}
