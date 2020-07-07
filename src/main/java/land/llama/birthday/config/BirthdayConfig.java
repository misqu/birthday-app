package land.llama.birthday.config;

public class BirthdayConfig {

    private final int workdaysBeforeBirthday;
    private final int workdaysBeforeBirthdayPick;
    private final int pickThreshold;
    private final int ageToCelebrate;

    public BirthdayConfig(int workdaysBeforeBirthday, int workdaysBeforeBirthdayPick, int pickThreshold,
                          int ageToCelebrate) {
        this.workdaysBeforeBirthday = workdaysBeforeBirthday;
        this.workdaysBeforeBirthdayPick = workdaysBeforeBirthdayPick;
        this.pickThreshold = pickThreshold;
        this.ageToCelebrate = ageToCelebrate;
    }

    public int getWorkdaysBeforeBirthday() {
        return workdaysBeforeBirthday;
    }

    public int getWorkdaysBeforeBirthdayPick() {
        return workdaysBeforeBirthdayPick;
    }

    public int getPickThreshold() {
        return pickThreshold;
    }

    public int getAgeToCelebrate() {
        return ageToCelebrate;
    }

}
