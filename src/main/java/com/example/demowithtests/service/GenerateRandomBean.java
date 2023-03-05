package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import org.springframework.stereotype.Service;

@Service
public class GenerateRandomBean implements GenerateRandom {

    private final static String[] nameM = {"Иван", "Петр", "Сидор", "Константин", "Александр", "Виктор", "Михаил", "Вячеслав", "Олег", "Юрий"};
    private final static String[] nameF = {"Наталья", "Татьяна", "Ирина", "Александра", "Юлия", "Нина", "Ольга", "Кристина", "Сюзанна", "Вероника"};
    private final static String[] secondName = {"Иванов", "Петров", "Сидоров", "Константинов", "Александров", "Викторов", "Михаилов", "Вячеславов", "Олегов", "Юрьев"};
    private final static String[] country = {"UA", "BM", "HT", "GR", "GE", "DK", "IN", "CA", "CU", "LT"};
    private final static String[] mailDomain = {"gmail.com", "mail.ru", "ukr.net", "bank.ua", "lanet.com", "hillel.com", "nova.ua", "avast.pl", "big.net", "yahoo.com"};
    private final static String[] localpart = {"iivanov", "ppetrov", "iirinova", "vviktorov", "oolgova", "vveronikova", "oolegov", "nninova", "vvycheslavov", "ttatyanova"};


    @Override
    public Employee getEmployee() {

        Employee employee = new Employee();

        if (random(1) == 1) {
            employee.setGender(Gender.M);
            employee.setName(nameM[random(nameM.length - 1)] + " " + secondName[random(secondName.length - 1)]);
        } else {
            employee.setGender(Gender.F);
            employee.setName(nameF[random(nameF.length - 1)] + " " + secondName[random(secondName.length - 1)] + "а");
        }

        employee.setCountry(country[random(country.length - 1)]);
        employee.setEmail(localpart[random(localpart.length - 1)] + "@" + mailDomain[random(mailDomain.length - 1)]);

        return employee;
    }


    /**
     * Генерация случайного числа от 0 до заданной верхней границы
     * @param upBound Верхняя граница
     * @return Случайное целое
     */
    private int random(int upBound) {
        return  upBound != 0 ? (int) ( Math.random() * (upBound + 1) ) : 0;
    }

}
