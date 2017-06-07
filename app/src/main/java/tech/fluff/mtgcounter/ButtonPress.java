package tech.fluff.mtgcounter;

import android.content.Context;
import android.os.Vibrator;


public class ButtonPress {
    public static void vibe(Context context, int time) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(time);
    }
}
