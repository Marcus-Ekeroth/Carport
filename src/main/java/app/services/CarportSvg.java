package app.services;

import app.controllers.OrderController;

public class CarportSvg {
    private int width;
    private int length;
    private Svg carportSvg;

    private int spaceAmount;
    private float spaceBetween;

    public CarportSvg(int width, int length) {
        this.width = width;
        this.length = length;
        carportSvg = new Svg(40, 40, "0 0 1000 1000", "75%");
        carportSvg.addRectangle(0, 0, width, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");

        spaceAmount = (int) Math.ceil(length/60);
        spaceBetween = length/spaceAmount;

        addLines();
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

            carportSvg.addRectangle((length - 30-100)/2+100, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle((length - 30-100)/2+100, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");

        }else if(length<=3*maxStolpeDis+extraSpaceFront+extraSpaceBehind){
            carportSvg.addRectangle(100, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(100, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - 30, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - 30, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");

            carportSvg.addRectangle((length - 30-100)/3+100, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle((length - 30-100)/3+100, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(2*(length - 30-100)/3+100, 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(2*(length - 30-100)/3+100, width - 35 - (4.5 / 2), 9.7, 9.7, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        }

    }
    private void addBeams() {
        carportSvg.addRectangle(0, 35, 4.5, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        carportSvg.addRectangle(0, width-35, 4.5, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");

    }
    private void addRafters(){

        for (int i=0; i <=  spaceAmount; i++)
        {
            carportSvg.addRectangle(i*spaceBetween,0,width,4.5,"stroke:#000000; fill: #ffffff");
        }
    }
    public Svg getCarportSvg() {
        return carportSvg;
    }
    public void addLines(){

        carportSvg.addDashedLine(spaceBetween, 35, length-spaceBetween, width-35, "Stroke:#000000");
        carportSvg.addDashedLine(spaceBetween, width-35, length-spaceBetween, 35, "Stroke:#000000");
    }

    @Override
    public String toString() {
        return carportSvg.toString();
    }


}


