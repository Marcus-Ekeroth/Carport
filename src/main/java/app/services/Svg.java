package app.services;

public class Svg {

    private static final String SVG_TEMPLATE = "<svg version=\"1.1\"\n" +
            "     x=\"%s\" y=\"%s\"\n" +
            "     viewBox=\"%s\" width=\"%s\"\n" +
            "     preserveAspectRatio=\"xMinYMin\">\n";
    private StringBuilder svg = new StringBuilder();

    private static final String SVG_ARROW_TEMPLATE = "<defs>\n" +
            "        <marker id=\"beginArrow\" markerWidth=\"12\" markerHeight=\"12\" refX=\"0\" refY=\"6\" orient=\"auto\">\n" +
            "            <path d=\"M0,6 L12,0 L12,12 L0,6\" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "        <marker id=\"endArrow\" markerWidth=\"12\" markerHeight=\"12\" refX=\"12\" refY=\"6\" orient=\"auto\">\n" +
            "            <path d=\"M0,0 L12,6 L0,12 L0,0 \" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "    </defs>";
    private static final String SVG_LINE_TEMPLATE = "<line x1=\"%d\"  y1=\"%d\" x2=\"%d\"   y2=\"%d\" \\n\" +\n" +
            "            \" style=\"stroke: #006600;\\n\" +\n" +
            "            \" marker-start: url(#beginArrow);\\n\" +\n" +
            "            \" marker-end: url(#endArrow);\" />";

    private static final String SVG_RECT_TEMPLATE = "<rect x=\"%.2f\" y=\"%.2f\" height=\"%.2f\" width=\"%.2f\" style=\"%s\" />";
    private static final String SVG_TEXT_TEMPLATE = "<text style=\"text-anchor: middle\" transform=\"translate(%d,%d) rotate(%d)\">%s cm</text>\n";

    public Svg(int x, int y, String viewBox, String width){
        svg.append(String.format(SVG_TEMPLATE,x,y,viewBox,width));
        svg.append(SVG_ARROW_TEMPLATE);
    }
    public void addRectangle(double x, double y, double height, double width, String style){
        svg.append(String.format(SVG_RECT_TEMPLATE, x, y, height, width, style));
    }
    public void addLine(int x1, int y1, int x2, int y2, String style){
        svg.append(String.format(SVG_LINE_TEMPLATE, x1, y1, x2, y2, style));
    }
    public void addArrow(int x1, int y1, int x2, int y2, String style){
    svg.append(String.format(SVG_LINE_TEMPLATE, x1, y1, x2, y2, style));

    }
    public void addText(int x1, int y1, int rotation1, String Text) {
        svg.append(String.format(SVG_TEXT_TEMPLATE, x1, y1, rotation1, Text));
    }
    public String addSvg(Svg innerSvg){
      return svg.append(innerSvg.toString()).toString();

    }

    @Override
    public String toString() {
        return svg.append("</svg>").toString();
    }
}
