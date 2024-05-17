package app.services;

import app.controllers.OrderController;

public class CarportSvg {
    private int width;
    private int length;
    private Svg carportSvg;


    public CarportSvg(int width, int length) {
        this.width = width;
        this.length = length;
        carportSvg = new Svg(40, 40, "0 0 855 690", "50%");
        carportSvg.addRectangle(0, 0, width, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        addBeams();
        addRafters();
        //addArrow();

    }

    private void addBeams() {
        carportSvg.addRectangle(0, 0, 4.5, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        carportSvg.addRectangle(0, width, 4.5, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");

    }
    private void addRafters(){
        int rafterAmount = (int) Math.ceil(length/60);
        float spaceBetween = length/rafterAmount;

        for (int i=0; i <= rafterAmount; i++)
        {
            carportSvg.addRectangle(i*spaceBetween,0,width,4.5,"stroke:#000000; fill: #ffffff");
        }
    }
    public Svg getCarportSvg() {
        return carportSvg;
    }
    public void addArrow(){

        carportSvg.addArrow(200,200,100,100,"stroke:#000000;");
        carportSvg.addArrow(500,400,700,400,"stroke:#000000;");
    }

    @Override
    public String toString() {
        return carportSvg.toString();
    }


}


