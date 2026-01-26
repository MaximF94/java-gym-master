package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private Coach coach;

    private int counter;

    public CounterOfTrainings(Coach coach, int counter) {
        this.coach = coach;
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "CounterOfTrainings{" +
                "coach=" + coach +
                ", counter=" + counter +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coach);
    }


    @Override
    public int compareTo(CounterOfTrainings o) {
        return o.counter - counter;
    }
}
