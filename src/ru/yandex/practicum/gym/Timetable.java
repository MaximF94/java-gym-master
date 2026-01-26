package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek,TreeMap<TimeOfDay,ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        ArrayList<TrainingSession> trainingSessionList;
        TreeMap<TimeOfDay,ArrayList<TrainingSession>> timeOfDayMap;

        if (timetable.containsKey(dayOfWeek)) {
            timeOfDayMap = timetable.get(dayOfWeek);
            if (timeOfDayMap.containsKey(timeOfDay)) {
                trainingSessionList = timeOfDayMap.get(timeOfDay);
            } else {
                trainingSessionList = new ArrayList<>();
                timeOfDayMap.put(timeOfDay,trainingSessionList);
            }
            trainingSessionList.add(trainingSession);
        } else {
            trainingSessionList = new ArrayList<>();
            trainingSessionList.add(trainingSession);
            timeOfDayMap = new TreeMap<>();
            timeOfDayMap.put(timeOfDay,trainingSessionList);
            timetable.put(dayOfWeek,timeOfDayMap);
        }

    }

    public TreeMap<TimeOfDay,ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek).get(timeOfDay);
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
                    if (traningsCountMap.containsKey(coach)) {
                        traningsCountMap.put(coach,traningsCountMap.get(coach) + 1);
                    } else {
                        traningsCountMap.put(coach,1);
                    }
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
