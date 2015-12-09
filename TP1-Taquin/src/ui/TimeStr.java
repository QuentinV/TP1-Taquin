package ui;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class TimeStr {
    private double time;

    public TimeStr(double time)
    {
        this.time = time;
    }

    @Override
    public String toString()
    {
        String str = "";
        int h = (int)time / 3600;
        if (h > 0)
        {
            str += String.valueOf(h)+"h";
            time -= h * 3600;
        }
        int min = (int)time / 60;
        if (min > 0)
        {
            str += String.valueOf(min) + "min";
            time -= min * 60;
        }

        if (time > 0)
        {
            DecimalFormat d = new DecimalFormat();
            d.setRoundingMode(RoundingMode.valueOf(2));
            str += d.format(time)+"s";
        }
        return str;
    }
}
