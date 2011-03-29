package org.gdteam.gwt.visualization.odometer.client;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDrawOptions;
import com.google.gwt.visualization.client.AbstractVisualization;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

public class OdometerVisualization extends AbstractVisualization<OdometerVisualization.OdometerVisualizationDrawOptions> {

    private static final String ID_COLORED_GRADIENT = "colored_gradient";
    private static final String ID_ITEM_GRADIENT = "item_gradient";
    
    private static final int ITEM_SIZE = 25;
    private static final double ROUND_XY = ITEM_SIZE / 5d;
    private static final double ROUND_ARG = ITEM_SIZE / 15d;
    private static final int SEPARATOR_SIZE = 1;
    private static final String SEPARATOR_COLOR = "#F7F7F7";
    private static final double TEXT_X_OFFSET = ITEM_SIZE / 3.3d;
    private static final double TEXT_Y_OFFSET = ITEM_SIZE / 1.39d;
    private static final double TEXT_SIZE = ITEM_SIZE / 1.47d;
    private static final String TEXT_COLOR = "#FFFFFF";
    
    private static final NumberFormat DECIMAL_FORMAT = NumberFormat.getFormat("#0.##");

    private HTML html;

    public OdometerVisualization() {        
        this.html = new HTML();
        initWidget(html);
        
    }

    @Override
    public void draw(AbstractDataTable data, OdometerVisualizationDrawOptions options) {
        
        Document doc = XMLParser.createDocument();
        
        Element svg = doc.createElement("svg");
        
        int width = ITEM_SIZE * data.getNumberOfColumns() + SEPARATOR_SIZE * data.getNumberOfColumns();
        svg.setAttribute("width", String.valueOf(width));
        svg.setAttribute("height", String.valueOf(ITEM_SIZE));
        svg.setAttribute("id", "odometer");
        svg.setAttribute("version", "1.1");
        
        doc.appendChild(svg);
        
        svg.appendChild(this.createDefs(doc, options));
        
        if (data.getNumberOfRows() <= 0 || data.getNumberOfColumns() <= 0) {
            return;
        }
        
        svg.appendChild(this.createLeftElement(doc));
      
        svg.appendChild(this.createText(doc, data.getFormattedValue(0, 0), 0));
        
        if (data.getNumberOfColumns() > 2) {
            for(int i=1;i<data.getNumberOfColumns() - 1;i++) {
                svg.appendChild(this.createSeparator(doc, i - 1));
                svg.appendChild(this.createItem(doc, i - 1));
                svg.appendChild(this.createText(doc, data.getFormattedValue(0, i), i));
            }
        }
        
        if (data.getNumberOfColumns() > 1) {
            svg.appendChild(this.createSeparator(doc, data.getNumberOfColumns() - 2));
            svg.appendChild(this.createRightElement(doc, data.getNumberOfColumns()));
            svg.appendChild(this.createText(doc, data.getFormattedValue(0, data.getNumberOfColumns() - 1), data.getNumberOfColumns() - 1));
        }
        
        StringBuilder shtml = new StringBuilder();
        
        if (options.isSvgwebEnabled()) {
            shtml.append("<![CDATA[");
            shtml.append("<script type=\"image/svg+xml\">").append(doc.toString()).append("</script>");
            shtml.append("]]>");
        } else {
            shtml.append(doc.toString());
        }
        this.html.setHTML(shtml.toString());
    }

    private Element createDefs(Document doc, OdometerVisualizationDrawOptions options) {
        
        Element ret = doc.createElement("defs");
 
        Element coloredGradient = doc.createElement("linearGradient");
        coloredGradient.setAttribute("id", ID_COLORED_GRADIENT);
        coloredGradient.setAttribute("x1", "0%");
        coloredGradient.setAttribute("y1", "-10%");
        coloredGradient.setAttribute("x2", "0%");
        coloredGradient.setAttribute("y2", "60%");
        
        Element coloredGradientStop1 = doc.createElement("stop");
        coloredGradientStop1.setAttribute("offset", "0%");
        coloredGradientStop1.setAttribute("style", "stop-color:#FFFFFF;stop-opacity:1" );
        coloredGradient.appendChild(coloredGradientStop1);
        
        Element coloredGradientStop2 = doc.createElement("stop");
        coloredGradientStop2.setAttribute("offset", "100%");
        coloredGradientStop2.setAttribute("style", "stop-color:" + options.getSpecialColor() + ";stop-opacity:1" );
        coloredGradient.appendChild(coloredGradientStop2);
        
        ret.appendChild(coloredGradient);
        
        Element itemGradient = doc.createElement("linearGradient");
        itemGradient.setAttribute("id", ID_ITEM_GRADIENT);
        itemGradient.setAttribute("x1", "0%");
        itemGradient.setAttribute("y1", "-10%");
        itemGradient.setAttribute("x2", "0%");
        itemGradient.setAttribute("y2", "60%");
        
        Element itemGradientStop1 = doc.createElement("stop");
        itemGradientStop1.setAttribute("offset", "0%");
        itemGradientStop1.setAttribute("style", "stop-color:#FFFFFF;stop-opacity:1" );
        itemGradient.appendChild(itemGradientStop1);
        
        Element itemGradientStop2 = doc.createElement("stop");
        itemGradientStop2.setAttribute("offset", "100%");
        itemGradientStop2.setAttribute("style", "stop-color:" + options.getItemColor() + ";stop-opacity:1" );
        itemGradient.appendChild(itemGradientStop2);
        
        ret.appendChild(itemGradient);
        
        return ret;
    }
    
    private Element createLeftElement(Document doc) {
        Element ret = doc.createElement("path");
        
        StringBuilder d = new StringBuilder();
        d.append("M 0,").append(DECIMAL_FORMAT.format(ROUND_XY)).append(" ");
        d.append("C 0,").append(DECIMAL_FORMAT.format(ROUND_ARG)).append(" ");
        d.append(DECIMAL_FORMAT.format(ROUND_ARG)).append(",0 ");
        d.append(DECIMAL_FORMAT.format(ROUND_XY)).append(",0 ");
        d.append("L ").append(DECIMAL_FORMAT.format(ITEM_SIZE)).append(",0 ");
        d.append("L ").append(DECIMAL_FORMAT.format(ITEM_SIZE)).append(",").append(DECIMAL_FORMAT.format(ITEM_SIZE)).append(" ");
        d.append("L ").append(DECIMAL_FORMAT.format(ROUND_XY)).append(",").append(DECIMAL_FORMAT.format(ITEM_SIZE)).append(" ");
        d.append("C ").append(DECIMAL_FORMAT.format(ROUND_ARG)).append(",").append(DECIMAL_FORMAT.format(ITEM_SIZE)).append(" ");
        d.append("0,").append(DECIMAL_FORMAT.format(ITEM_SIZE - ROUND_ARG)).append(" ");
        d.append("0,").append(DECIMAL_FORMAT.format(ITEM_SIZE - ROUND_XY)).append(" ");
        d.append("L 0,").append(DECIMAL_FORMAT.format(ROUND_XY)).append(" Z");
        
        ret.setAttribute("d", d.toString());
        ret.setAttribute("style", "fill:url(#" + ID_ITEM_GRADIENT + ")");
        
        return ret;
    }
    
    private Element createRightElement(Document doc, int totalItems) {
        Element ret = doc.createElement("path");
        
        double start = ((totalItems) * (ITEM_SIZE + SEPARATOR_SIZE)) - SEPARATOR_SIZE;
        
        StringBuilder d = new StringBuilder();
        d.append("M ").append(DECIMAL_FORMAT.format(start)).append(",").append(DECIMAL_FORMAT.format(ROUND_XY)).append(" ");
        d.append("C ").append(DECIMAL_FORMAT.format(start)).append(",").append(DECIMAL_FORMAT.format(ROUND_ARG)).append(" ");
        d.append(DECIMAL_FORMAT.format(start - ROUND_ARG)).append(", 0 ");
        d.append(DECIMAL_FORMAT.format(start - ROUND_XY)).append(", 0 ");
        d.append("L ").append(DECIMAL_FORMAT.format(start - ITEM_SIZE)).append(", 0 ");
        d.append("L ").append(DECIMAL_FORMAT.format(start - ITEM_SIZE)).append(",").append(DECIMAL_FORMAT.format(ITEM_SIZE)).append(" ");
        d.append("L ").append(DECIMAL_FORMAT.format(start - ROUND_XY)).append(",").append(DECIMAL_FORMAT.format(ITEM_SIZE)).append(" ");
        d.append("c ").append(DECIMAL_FORMAT.format(ROUND_XY - ROUND_ARG)).append(",0 ");
        d.append(DECIMAL_FORMAT.format(ROUND_XY)).append(",").append(DECIMAL_FORMAT.format(0d - ROUND_ARG)).append(" ");
        d.append(DECIMAL_FORMAT.format(ROUND_XY)).append(",").append(DECIMAL_FORMAT.format(0 - ROUND_XY)).append(" z");
        
        ret.setAttribute("d", d.toString());
        ret.setAttribute("style", "fill:url(#" + ID_COLORED_GRADIENT + ")");
        
        return ret;
    }
    
    private Element createSeparator(Document doc, int separatorIndex) {
        Element ret = doc.createElement("rect");
        
        double x = (separatorIndex * SEPARATOR_SIZE) + ((separatorIndex + 1) * ITEM_SIZE);
        
        ret.setAttribute("x", DECIMAL_FORMAT.format(x));
        ret.setAttribute("y", "0");
        ret.setAttribute("width", DECIMAL_FORMAT.format(SEPARATOR_SIZE));
        ret.setAttribute("height", DECIMAL_FORMAT.format(ITEM_SIZE));
        
        ret.setAttribute("style", "fill:" + SEPARATOR_COLOR);
        
        return ret;
    }
    
    private Element createItem(Document doc, int itemIndex) {
        Element ret = doc.createElement("rect");
        
        double x = (itemIndex + 1) * (SEPARATOR_SIZE + ITEM_SIZE);
        
        ret.setAttribute("x", DECIMAL_FORMAT.format(x));
        ret.setAttribute("y", "0");
        ret.setAttribute("width", DECIMAL_FORMAT.format(ITEM_SIZE));
        ret.setAttribute("height", DECIMAL_FORMAT.format(ITEM_SIZE));
        
        ret.setAttribute("style", "fill:url(#" + ID_ITEM_GRADIENT + ")");
        
        return ret;
    }
    
    private Element createText(Document doc, String value, int textIndex) {
        
        Element ret = doc.createElement("text");
        ret.setNodeValue(value);
        
        double x = (textIndex) * (ITEM_SIZE + SEPARATOR_SIZE) + TEXT_X_OFFSET;
        ret.setAttribute("x", DECIMAL_FORMAT.format(x));
        ret.setAttribute("y", DECIMAL_FORMAT.format(TEXT_Y_OFFSET));
        
        ret.setAttribute("font-size", DECIMAL_FORMAT.format(TEXT_SIZE));
        ret.setAttribute("font-weight", "bold");
        ret.setAttribute("font-family", "Arial");
        ret.setAttribute("style", "fill:" + TEXT_COLOR);
        
        ret.appendChild(doc.createTextNode(value));
        
        return ret;
    }
     

    public static class OdometerVisualizationDrawOptions extends AbstractDrawOptions {
        protected OdometerVisualizationDrawOptions() {

        }

        public final native String getItemColor() /*-{
			return this.itemColor || "#000000";
        }-*/;

        public final native void setItemColor(String color) /*-{
			this.itemColor = color;
        }-*/;

        public final native String getSpecialColor() /*-{
          return this.specialColor || "#FF00B0";
        }-*/;

        public final native void setSpecialColor(String color) /*-{
          this.specialColor = color;
        }-*/;
        
        public final native boolean isSvgwebEnabled() /*-{
            return this.svgwebEnabled || false;
        }-*/;

      public final native void setSvgwebEnabled(boolean enabled) /*-{
            this.svgwebEnabled = enabled;
      }-*/;
    }

}
