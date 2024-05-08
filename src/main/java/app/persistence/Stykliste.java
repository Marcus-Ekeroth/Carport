package app.persistence;

public class Stykliste {


   int calcStolpeAmount(int carportLength){
        int amount = 4;
        if(carportLength>(310+100+30)){
            amount = 6;
        }
        return amount;
   }

    int calcRemAmount(int carportLength){
        int amount = 4;

        if(carportLength>(310+100+30)){
            amount = 6;
        }


        return amount;
    }


}
