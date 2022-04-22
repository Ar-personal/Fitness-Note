package com.reitech.gym.ui.programs;

public class Program {

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
    private double bench3RepMax;
    private double squat3RepMax;
    private double dead3RepMax;
    private double ohp3RepMax;

    public Program(String name, int unitDefault, int weightIncrementDefault){
        this.name = name;
        this.unitDefault = unitDefault;
        this.weightIncrementDefault = weightIncrementDefault;

    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBench3RepMax() {
        return bench3RepMax;
    }

    public void setBench3RepMax(double bench3RepMax) {
        this.bench3RepMax = bench3RepMax;
    }

    public double getSquat3RepMax() {
        return squat3RepMax;
    }

    public void setSquat3RepMax(double squat3RepMax) {
        this.squat3RepMax = squat3RepMax;
    }

    public double getDead3RepMax() {
        return dead3RepMax;
    }

    public void setDead3RepMax(double dead3RepMax) {
        this.dead3RepMax = dead3RepMax;
    }

    public double getOhp3RepMax() {
        return ohp3RepMax;
    }

    public void setOhp3RepMax(double ohp3RepMax) {
        this.ohp3RepMax = ohp3RepMax;
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
