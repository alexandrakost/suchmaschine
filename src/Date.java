public class Date {
    private int day;
    private int month;
    private int year;
    public static final int [] months31 = {1, 3, 5, 7, 8, 10, 12};

    public Date (){
        this.day = Terminal.TODAYS_DAY;
        this.month = Terminal.TODAYS_MONTH;
        this.year = Terminal.TODAYS_YEAR;
    }

    public Date (int theDay, int theMonth, int theYear){
        this.setDay(theDay);
        this.setMonth(theMonth);
        this.setYear(theYear);
    }

    public void setDay (int day){
        if (day >= 1 & day <= 31) {
            this.day = day;
        }
    }
    public void setMonth(int month){
        if (month >= 1 & month <= 12) {
            this.month = month;
        }
    }
    public void setYear (int year){
        if (year >= 1 & year <= Terminal.TODAYS_YEAR) {
            this.year = year;
        }
    }
    public int getDay (){
        return this.day;
    }
    public int getMonth (){
        return this.month;
    }
    public int getYear (){
        return this.year;
    }

    // schaut ob Jahr ein Schaltjahr ist und gibt die Anzahl der Tage im Jahr zurück
    private int daysInYear (int Year) {
        if (Year % 100 == 0 ) {
            if (Year % 400 == 0) {
                return 366;
            }
        } else {
            if (Year % 4 == 0) {
                return 366;
            }
        } return 365;

    }
    // gibt die Anzahl der Tage im Februar abhänig zurück gegeben die Anzahl der Tage im Jahr
    private int daysFebruary (int Year) {
        if (daysInYear(Year) == 366) {
            return 29;
        } return 28;
    }

    // schaut wieviel Tage der Monat hat
    private int daysInMonth (int Month, int Year) {
        int result = 0;
        for (int i = 0; i <=6; i++) {
            if (Month == months31[i]){
                result = 31;
            } else {
                // ergänzen um schaltjahr
                if (Month == 2){
                    result = daysFebruary(Year);
                } else {
                    result = 30;
                }
            }
        } return result;

    }

    // schaut wieviele Schaltjahre gab es seit 1900 bis zum Vorjahr of this.year
    private int NumberLeapyearsSince1900 (int YearNumber) {
        int i = 1900;
        int countLeapyears = 0;
        for (i = 1900; i < YearNumber; i = i+4); {
            if (daysInYear(i) == 366) {
                countLeapyears ++;
            }
        } return countLeapyears;

    }

    public String toString (){
        return this.day + "." + this.month + "." + this.year +".";
    }

    /**
     * tage seit 1900 bis zum Datum des Dokumentes
     * @return
     */
    private int daysSince1900(){
        return this.day + this.month*daysInMonth(this.getMonth(), this.getYear()) +
                (this.year- 1900)*365 + NumberLeapyearsSince1900(this.getYear());
    }

    // tage seit dem Datum des Dokumentes bis heute = Alter des Dokumentes
    public int getAgeInDays (Date today) {
        int daysSince1900tillToday = today.day + today.month*daysInMonth(today.month, today.year) +
                (today.year - 1900)*365 + NumberLeapyearsSince1900(today.year);
        return daysSince1900tillToday - daysSince1900();
    }

    //new get age in years
    public int getAgeInYears (Date today) {
        int maxAgeInYears = today.year - this.year;

        if (today.month > this.month || (today.month == this.month && today.day >= this.day)){
            return maxAgeInYears;
        }

        return maxAgeInYears - 1;
    }

    public boolean equals (Date date){
        if (date == null) {
            return false;
        }

        if (this.day == date.day && this.month == date.month && this.year == date.year){
            return true;
        }

        return false;
    }
}
