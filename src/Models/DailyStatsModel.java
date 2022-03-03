package Models;

import java.util.ArrayList;

import Interfaces.ModelObserver;
import Interfaces.ViewObserver;

/**
 * DailyStatsModel
 * A class to handle reading and writing of Product entities.
 */
public class DailyStatsModel implements ModelObserver {
    // private final String WARNING_PREFIX = "\n[ WARNING ] ";
    // private final String SUCCESS_PREFIX = "\n[ SUCCESS ] ";
    private ArrayList<ViewObserver> observers = new ArrayList<>();
    private int dailySales = 0;
    private double revenues = 0.0;
    private double expenses = 0.0;
    private int currentDay = 0;

    public DailyStatsModel() {
    }

    @Override
    public void registerObserver(ViewObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(ViewObserver observer) {
        this.observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (ViewObserver observer : this.observers)
            observer.notifyObserver();
    }

    public int getDailySales() {
        return this.dailySales;
    }

    public int getCurrentDay() {
        return this.currentDay;
    }

    public double getRevenues() {
        return this.revenues;
    }

    public double getExpenses() {
        return this.expenses;
    }

    public double getProfit() {
        return (this.revenues - this.expenses);
    }

    public void addSales(int sales) {
        this.dailySales += sales;
    }

    public void addRevenues(double revenue) {
        this.revenues += revenue;
    }

    public void addExpenses(double expenses) {
        this.expenses += expenses;
    }

    /**
     * Adds plus one to the current day.
     */
    public void nextDay() {
        this.currentDay++;
    }
}