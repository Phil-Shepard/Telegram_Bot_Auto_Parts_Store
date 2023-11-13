package telegram.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.cars.Bmw;
import telegram.cars.Car;
import telegram.cars.Lada;
import telegram.cars.Reno;

import java.util.ArrayList;
import java.util.List;

public class Logic {
    private Bmw bmw = new Bmw("BMW", "колёса ","двигатель ",null,null,"дворники");
    private Reno reno = new Reno("Reno","колёса ",null,"зеркала ","фары ",null);
    private Lada lada = new Lada("Lada", "колёса ","двигатель ","зеркала ","фары ","дворники");


    public List<Car> getCars(){
        List<Car> listCars = new ArrayList<Car>();
        listCars.add(bmw);
        listCars.add(reno);
        listCars.add(lada);
        return listCars;
    }
}
