//package telegram.bot;
//
//import com.urfu.bot.domain.car.Bmw;
//import com.urfu.bot.domain.car.Lada;
//import com.urfu.bot.domain.car.Renault;
//import telegram.cars.Car;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Logic {
//    private Bmw bmw = new Bmw("BMW", "колёса ","двигатель ",null,null,"дворники");
//    private Renault reno = new Renault("Reno","колёса ",null,"зеркала ","фары ",null);
//    private Lada lada = new Lada("Lada", "колёса ","двигатель ","зеркала ","фары ","дворники");
//
//
//    public List<Car> getCars(){
//        List<Car> listCars = new ArrayList<Car>();
//        listCars.add(bmw);
//        listCars.add(reno);
//        listCars.add(lada);
//        return listCars;
//    }
//
//    public Car getCarsName(String name){
//        List<Car> list = getCars();
//        for(Car car: list){
//            if(car.getName() == name){
//                return car;
//            }
//        }
//        return bmw;
//    }
//}
