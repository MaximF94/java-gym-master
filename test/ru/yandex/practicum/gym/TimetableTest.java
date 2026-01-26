package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1,timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TimeOfDay firstTrainingTime = new TimeOfDay(13, 0);
        TimeOfDay secondTrainingTime = new TimeOfDay(20, 0);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1,timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertTrue(
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstKey().equals(firstTrainingTime) &&
                        timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastKey().equals(secondTrainingTime));
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdults = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Николаев", "Игорь", "Александрович");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession singleTrainingSession2 = new TrainingSession(group, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));

        TrainingSession singleTrainingSession3 = new TrainingSession(groupAdults, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);

        TimeOfDay trainingTimeFirst = new TimeOfDay(13, 0);
        TimeOfDay trainingTimeSecond = new TimeOfDay(14, 0);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(1,timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,trainingTimeFirst).size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,trainingTimeSecond));

        //Проверить, что за вторник в 13:00 вернулись два занятия
        Assertions.assertEquals(2,timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY,trainingTimeFirst).size());
    }

    @Test
    public void testAddNewTrainingSession() {
        Timetable timetable = new Timetable();

        DayOfWeek day = DayOfWeek.MONDAY;
        TimeOfDay trainingTime = new TimeOfDay(13, 0);

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                day, trainingTime);

        timetable.addNewTrainingSession(singleTrainingSession);
        
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessions = timetable.getTrainingSessionsForDay(day);

        //Проверяем, что занятие добавлено
        Assertions.assertNotNull(sessions.get(trainingTime));
    }


    @Test
    void testGetCountByCoaches() {

        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для Взрослых", Age.ADULT, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Николаев", "Игорь", "Александрович");
        Coach coach3 = new Coach("Смирнов", "Чувак", "Ольгович");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession3= new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession4= new TrainingSession(group2, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession5= new TrainingSession(group2, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession6= new TrainingSession(group2, coach2,
                DayOfWeek.FRIDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession7= new TrainingSession(group2, coach3,
                DayOfWeek.SATURDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);
        timetable.addNewTrainingSession(singleTrainingSession4);
        timetable.addNewTrainingSession(singleTrainingSession5);
        timetable.addNewTrainingSession(singleTrainingSession6);
        timetable.addNewTrainingSession(singleTrainingSession7);

        //Проверить, что вернулось 3 тренера по всем занятиям за неделю
        Assertions.assertEquals(3,timetable.getCountByCoaches().size());

        //Проверить, что максимальное количество занятий 4 у тренера, который в начале списка
        CounterOfTrainings counterOfTrainingsMax = timetable.getCountByCoaches().getFirst();
        CounterOfTrainings counterOfTrainingsMin = timetable.getCountByCoaches().getLast();
        Assertions.assertEquals(4,counterOfTrainingsMax.getCounter());

        //проверить, что тренеры выводятся в порядке убывания по количеству тренировок вывести первого и последнего
        Assertions.assertTrue(counterOfTrainingsMax.getCounter() == 4 &&
                counterOfTrainingsMin.getCounter() == 1);

    }

}
