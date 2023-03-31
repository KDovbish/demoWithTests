package com.example.demowithtests.domain;

public enum PassportState {
    NEW,        /* Новый */
    ACTIVE,     /* Активный */
    EXPIRED,    /* Неактивный - Истек строк действия */
    STOLEN,     /* Неактивный - Украден */
    DISREPAIR   /* Неактивный - Пришел в негодность */
}
