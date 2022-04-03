package cz.cvut.fel.pjv.aspone.utils;

/**
 * The type Clock.
 *
 * @author Mukan Atazhanov
 * @project atazhmuk
 * @created 01 /04/2022 - 16:14 Clock class shows time
 */
public class Clock {
    private int hh;
    private int mm;
    private int ss;

    /**
     * Instantiates a new Clock.
     *
     * @param hh the hh
     * @param mm the mm
     * @param ss the ss
     */
    public Clock(int hh, int mm, int ss) {
        this.hh = hh;
        this.mm = mm;
        this.ss = ss;
    }

    /**
     * Out of time boolean.
     *
     * @return the boolean
     */
    public boolean outOfTime() {
        return (hh == 0 && mm == 0 && ss == 0);
    }

    /**
     * Decr.
     */
    public void decr() {
        if (this.mm == 0 && this.ss == 0) {
            this.ss = 59;
            this.mm = 59;
            this.hh--;
        } else if (this.ss == 0) {
            this.ss = 59;
            this.mm--;
        } else this.ss--;
    }

    @Override
    public String toString() {
        return hh + ":" + mm + ":" + ss;
    }
}
