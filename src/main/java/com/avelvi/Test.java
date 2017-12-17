package com.avelvi;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Test {

    private Long id;
    private String name;
    private LocalDate currentDate;
    private LocalDateTime currentDateTime;
    private Boolean isIdOdd;

    public Test() {
    }

    public Test(Long id, String name, LocalDate currentDate, LocalDateTime currentDateTime, Boolean isIdOdd) {
        this.id = id;
        this.name = name;
        this.currentDate = currentDate;
        this.currentDateTime = currentDateTime;
        this.isIdOdd = isIdOdd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public Boolean getIdOdd() {
        return isIdOdd;
    }

    public void setIdOdd(Boolean idOdd) {
        isIdOdd = idOdd;
    }
}
