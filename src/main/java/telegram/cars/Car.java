package telegram.cars;

import telegram.config.BotConfig;

public class Car {

    protected String name;
    protected String wheels;
    protected String motor;
    protected String mirrors;
    protected String headlights;
    protected String wipers;

    public Car(String name, String wheels, String motor, String mirrors, String headlights, String wipers){
        this.name = name;
        this.wheels = wheels;
        this.motor = motor;
        this.mirrors = mirrors;
        this.headlights = headlights;
        this.wipers = wipers;
    }

    public String getName(){
        return name;
    }

    public String AvailabilityParts(){
        return getWheels()+ getMotor() + getMirros() + getHeadlights() + getWipers();
    }

    public String getWheels(){
        if (wheels == null)
            wheels = "";
        return wheels;
    }

    public String getMotor(){
        if (motor == null)
            motor = "";
        return motor;
    }

    public String getMirros(){
        if (mirrors == null)
            mirrors = "";
        return mirrors;
    }

    public String getHeadlights(){
        if (headlights == null)
            headlights = "";
        return headlights;
    }

    public String getWipers(){
        if (wipers == null)
            wipers = "";
        return wipers;
    }
}
