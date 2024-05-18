package app.services;

import app.controllers.OrderController;

public class CarportSvg {
    private int width;
    private int length;
    private Svg carportSvg;


    public CarportSvg(int width, int length) {
        this.width = width;
        this.length = length;
        carportSvg = new Svg(40, 40, "0 0 855 690", "75%");
        carportSvg.addRectangle(0, 0, width, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");

        //addArrow();
        addBeams();
        addPoles();
        addRafters();


    }
    private void addPoles() {
        int maxStolpeDis = 310;
        int extraSpaceFront = 100;
        int extraSpaceBehind = 30;
        if(length<=maxStolpeDis+2*extraSpaceBehind) {
            carportSvg.addRectangle(30, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(30, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - 30, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - 30, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        }else if(length<=maxStolpeDis+extraSpaceFront+extraSpaceBehind){
            carportSvg.addRectangle(100, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(100, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - 30, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - 30, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        }else if(length<=2*maxStolpeDis+extraSpaceFront+extraSpaceBehind){
            carportSvg.addRectangle(100, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(100, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - 30, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - 30, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");

            carportSvg.addRectangle((100+length - 30)/2, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle((100+length - 30)/2, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");

        }else if(length<=3*maxStolpeDis+extraSpaceFront+extraSpaceBehind){
            carportSvg.addRectangle(100, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(100, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - 30, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - 30, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");

            carportSvg.addRectangle((100+length - 30)/3, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle((100+length - 30)/3, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(2*(100+length - 30)/3, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(2*(100+length - 30)/3, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        }

    }
    private void addBeams() {
        carportSvg.addRectangle(0, 35, 4.5, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        carportSvg.addRectangle(0, width-35, 4.5, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");

    }
    private void addRafters(){
        int spaceAmount = (int) Math.ceil(length/60);

        float spaceBetween = length/spaceAmount;

        for (int i=0; i <=  spaceAmount; i++)
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


