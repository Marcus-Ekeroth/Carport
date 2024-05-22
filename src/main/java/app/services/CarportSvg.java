package app.services;

public class CarportSvg {
    private int width;
    private int length;
    private Svg carportSvg;
    private final int spaceBetweenRaftersAmount;
    private final float spaceBetweenRafters;

    private final int extraSpaceFront = 100;
    private final int extraSpaceBehind = 30;
    private final int maxPoleDis = 310;
    private final int beamSpace = 35;
    private final double rafterWidth = 4.5;
    private final double poleWidth = 9.7;

    public CarportSvg(int width, int length) {
        this.width = width;
        this.length = length;
        carportSvg = new Svg(40, 40, "0 0 1000 1000", "75%");
        carportSvg.addRectangle(0, 0, width, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");

        spaceBetweenRaftersAmount = (int) Math.ceil(length/60);
        spaceBetweenRafters = length/ spaceBetweenRaftersAmount;

        addLines();
        addBeams();
        addPoles();
        addRafters();




    }
    private void addPoles() {

        if(length<=maxPoleDis+2*extraSpaceBehind) {
            carportSvg.addRectangle(extraSpaceBehind, beamSpace - (rafterWidth/ 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(extraSpaceBehind, width - beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - extraSpaceBehind, beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - extraSpaceBehind, width - beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        }else if(length<=maxPoleDis+extraSpaceFront+extraSpaceBehind){
            carportSvg.addRectangle(extraSpaceFront, beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(extraSpaceFront, width - beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - extraSpaceBehind, beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - extraSpaceBehind, width - beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        }else if(length<=2*maxPoleDis+extraSpaceFront+extraSpaceBehind){
            carportSvg.addRectangle(extraSpaceFront, beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(extraSpaceFront, width - beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - extraSpaceBehind, beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - extraSpaceBehind, width - beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");

            carportSvg.addRectangle((length - extraSpaceBehind-extraSpaceFront)/2+extraSpaceFront, beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle((length - extraSpaceBehind-extraSpaceFront)/2+extraSpaceFront, width - beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");

        }else if(length<=3*maxPoleDis+extraSpaceFront+extraSpaceBehind){
            carportSvg.addRectangle(extraSpaceFront, beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(extraSpaceFront, width - beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - extraSpaceBehind, beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(length - extraSpaceBehind, width - beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");

            carportSvg.addRectangle((length - extraSpaceBehind-extraSpaceFront)/3+extraSpaceFront, beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle((length - extraSpaceBehind-extraSpaceFront)/3+extraSpaceFront, width - beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(2*(length - extraSpaceBehind-extraSpaceFront)/3+extraSpaceFront, beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
            carportSvg.addRectangle(2*(length - extraSpaceBehind-extraSpaceFront)/3+extraSpaceFront, width - beamSpace - (rafterWidth / 2), poleWidth, poleWidth, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        }

    }
    private void addBeams() {
        carportSvg.addRectangle(0, beamSpace, rafterWidth, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");
        carportSvg.addRectangle(0, width-beamSpace, rafterWidth, length, "stroke-width:1px; stroke: #000000; fill: #ffffff");

    }
    private void addRafters(){

        for (int i = 0; i <= spaceBetweenRaftersAmount; i++)
        {
            carportSvg.addRectangle(i* spaceBetweenRafters,0,width,rafterWidth,"stroke:#000000; fill: #ffffff");
        }
    }
    public Svg getCarportSvg() {
        return carportSvg;
    }
    public void addLines(){

        carportSvg.addDashedLine(spaceBetweenRafters, beamSpace, length- spaceBetweenRafters, width-beamSpace, "Stroke:#000000");
        carportSvg.addDashedLine(spaceBetweenRafters, width-beamSpace, length- spaceBetweenRafters, beamSpace, "Stroke:#000000");
    }

    @Override
    public String toString() {
        return carportSvg.toString();
    }


}


