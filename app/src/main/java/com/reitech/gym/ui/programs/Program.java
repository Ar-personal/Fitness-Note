package com.reitech.gym.ui.programs;

public class Program {

    private int programID;
    private String name;
    private int unitDefault = 0;
    private int weightIncrementDefault = 0;
    private double maxIncreaseDefault = 5;
    private double benchMax, squatMax, deadliftMax, ohpMax;
    private int daysCompleted = 0;
    private int streak = 0;
    private String description;
    private int imageResouceID;
    private int benchFail = 0;
    private int squatFail = 0;
    private int deadFail = 0;
    private int ohpFail = 0;
    private int benchFailT2 = 0;
    private int squatFailT2 = 0;
    private int deadFailT2 = 0;
    private int ohpFailT2 = 0;
    private double benchT1ThreeRep;
    private double benchT2TenRep;
    private double squatT1ThreeRep;
    private double squatT2TenRep;
    private double deadT1ThreeRep;
    private double deadT2TenRep;
    private double ohpT1ThreeRep;
    private double ohpT2TenRep;

    public Program(String name, int unitDefault, int weightIncrementDefault){
        this.name = name;
        this.unitDefault = unitDefault;
        this.weightIncrementDefault = weightIncrementDefault;

    }



    public int getExerciseFail(String exercise, int tier){
        int result = 0;
        if(tier == 1) {
            switch (exercise) {
                case "Barbell Squat":
                    result = squatFail;
                    break;
                case "Dead-lift":
                    result =  deadFail;
                    break;
                case "Flat Barbell Bench Press":
                    result =  benchFail;
                    break;
                case "Overhead Press":
                    result =  ohpFail;
                    break;
            }
        }else if (tier == 2) {
            switch (exercise) {
                case "Barbell Squat":
                    result =  squatFailT2;
                    break;
                case "Dead-lift":
                    result =  deadFailT2;
                    break;
                case "Flat Barbell Bench Press":
                    result =  benchFailT2;
                    break;
                case "Overhead Press":
                    result =  ohpFailT2;
                    break;
            }
        }
        return result;

    }

    public int calculateT1RepsFromFails(int fails, String programName){
        switch (programName){
            case "GZCLP":
                switch (fails){
                    case 1:
                        return 2;
                    case 2:
                        return 1;
                    default:
                        return 3;
                }
        }
        return -1;
    }

    public int calculateT2RepsFromFails(int fails, String programName){
        switch (programName){
            case "GZCLP":
                switch (fails){
                    case 1:
                        return 8;
                    case 2:
                        return 6;
                    default:
                        return 10;
                }
        }
        return -1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProgramID(int programID) {
        this.programID = programID;
    }

    public int getProgramID() {
        return programID;
    }

    public int getBenchFailT2() {
        return benchFailT2;
    }

    public void setBenchFailT2(int benchFailT2) {
        this.benchFailT2 = benchFailT2;
    }

    public int getSquatFailT2() {
        return squatFailT2;
    }

    public void setSquatFailT2(int squatFailT2) {
        this.squatFailT2 = squatFailT2;
    }

    public int getDeadFailT2() {
        return deadFailT2;
    }

    public void setDeadFailT2(int deadFailT2) {
        this.deadFailT2 = deadFailT2;
    }

    public int getOhpFailT2() {
        return ohpFailT2;
    }

    public void setOhpFailT2(int ohpFailT2) {
        this.ohpFailT2 = ohpFailT2;
    }


    public double getBenchT1ThreeRep() {
        return benchT1ThreeRep;
    }

    public void setBenchT1ThreeRep(double benchT1ThreeRep) {
        this.benchT1ThreeRep = benchT1ThreeRep;
    }

    public double getBenchT2TenRep() {
        return benchT2TenRep;
    }

    public void setBenchT2TenRep(double benchT2TenRep) {
        this.benchT2TenRep = benchT2TenRep;
    }

    public double getSquatT1ThreeRep() {
        return squatT1ThreeRep;
    }

    public void setSquatT1ThreeRep(double squatT1ThreeRep) {
        this.squatT1ThreeRep = squatT1ThreeRep;
    }

    public double getSquatT2TenRep() {
        return squatT2TenRep;
    }

    public void setSquatT2TenRep(double squatT2TenRep) {
        this.squatT2TenRep = squatT2TenRep;
    }

    public double getDeadT1ThreeRep() {
        return deadT1ThreeRep;
    }

    public void setDeadT1ThreeRep(double deadT1ThreeRep) {
        this.deadT1ThreeRep = deadT1ThreeRep;
    }

    public double getDeadT2TenRep() {
        return deadT2TenRep;
    }

    public void setDeadT2TenRep(double deadT2TenRep) {
        this.deadT2TenRep = deadT2TenRep;
    }

    public double getOhpT1ThreeRep() {
        return ohpT1ThreeRep;
    }

    public void setOhpT1ThreeRep(double ohpT1ThreeRep) {
        this.ohpT1ThreeRep = ohpT1ThreeRep;
    }

    public double getOhpT2TenRep() {
        return ohpT2TenRep;
    }

    public void setOhpT2TenRep(double ohpT2TenRep) {
        this.ohpT2TenRep = ohpT2TenRep;
    }

    public int getImageResouceID() {
        return imageResouceID;
    }

    public void setImageResouceID(int imageResouceID) {
        this.imageResouceID = imageResouceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDaysCompleted() {
        return daysCompleted;
    }

    public void setDaysCompleted(int daysCompleted) {
        this.daysCompleted = daysCompleted;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public String getName() {
        return name;
    }

    public int getUnitDefault() {
        return unitDefault;
    }

    public int getWeightIncrementDefault() {
        return weightIncrementDefault;
    }

    public double getMaxIncreaseDefault() {
        return maxIncreaseDefault;
    }

    public void setMaxIncreaseDefault(double maxIncreaseDefault) {
        this.maxIncreaseDefault = maxIncreaseDefault;
    }

    public double getBenchMax() {
        return benchMax;
    }

    public void setBenchMax(double benchMax) {
        this.benchMax = benchMax;
    }

    public double getSquatMax() {
        return squatMax;
    }

    public void setSquatMax(double squatMax) {
        this.squatMax = squatMax;
    }

    public double getDeadliftMax() {
        return deadliftMax;
    }

    public void setDeadliftMax(double deadliftMax) {
        this.deadliftMax = deadliftMax;
    }

    public double getOhpMax() {
        return ohpMax;
    }

    public void setOhpMax(double ohpMax) {
        this.ohpMax = ohpMax;
    }

    public int getBenchFail() {
        return benchFail;
    }

    public void setBenchFail(int benchFail) {
        this.benchFail = benchFail;
    }

    public int getSquatFail() {
        return squatFail;
    }

    public void setSquatFail(int squatFail) {
        this.squatFail = squatFail;
    }

    public int getDeadFail() {
        return deadFail;
    }

    public void setDeadFail(int deadFail) {
        this.deadFail = deadFail;
    }

    public int getOhpFail() {
        return ohpFail;
    }

    public void setOhpFail(int ohpFail) {
        this.ohpFail = ohpFail;
    }
}
