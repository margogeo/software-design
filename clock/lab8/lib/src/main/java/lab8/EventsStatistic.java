package lab8;

public interface EventsStatistic {
    void incEvent(String name);

    double getEventStatisticByName(String name);

    double getAllEventStatistic();

    void printStatistic();
}
