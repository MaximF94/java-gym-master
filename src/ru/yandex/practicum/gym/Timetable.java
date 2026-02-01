package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek,TreeMap<TimeOfDay,ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public Timetable() {
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        ArrayList<TrainingSession> trainingSessionList;
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> timeOfDayMap = timetable.get(dayOfWeek);

        timeOfDayMap.compute(timeOfDay, (timeKey, sessionList) -> {
           if (sessionList == null) {
               sessionList = new ArrayList<>();
           }
           sessionList.add(trainingSession);
           return sessionList;
        });
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        ArrayList<TrainingSession> trainingSessions = new ArrayList<>();
        for (ArrayList<TrainingSession> sessionArrayList : timetable.get(dayOfWeek).values()) {
            trainingSessions.addAll(sessionArrayList);
        }
        return trainingSessions;
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek).getOrDefault(timeOfDay,new ArrayList<>());
    }

    public TreeSet<CounterOfTrainings> getCountByCoaches() {
        Map<Coach,Integer> traningsCountMap = new HashMap<>();
        TreeSet<CounterOfTrainings> counterOfTrainingsTreeSet = new TreeSet<>();
        CounterOfTrainings counterOfTrainings = null;
        Coach coach = null;

        for (TreeMap<TimeOfDay,ArrayList<TrainingSession>> treeMapCoach : timetable.values()) {
            for (ArrayList<TrainingSession> trainingSessions : treeMapCoach.values()) {
                for (int i = 0; i < trainingSessions.size(); i++) {
                    coach = trainingSessions.get(i).getCoach();
                    traningsCountMap.compute(coach, (key, oldValue) -> {
                        if (oldValue == null) {
                            return 1;
                        } else {
                            return oldValue + 1;
                        }
                    });
                }
            }
        }

        for (Map.Entry<Coach, Integer> entry : traningsCountMap.entrySet()) {
            counterOfTrainings = new CounterOfTrainings(entry.getKey(),entry.getValue());
            counterOfTrainingsTreeSet.add(counterOfTrainings);
        }

        return counterOfTrainingsTreeSet;
    }
}
