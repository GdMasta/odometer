package org.gdteam.gwt.visualization.odometer.client;

import org.vectomatic.dom.svg.OMSVGDefsElement;
import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGLength;
import org.vectomatic.dom.svg.OMSVGLinearGradientElement;
import org.vectomatic.dom.svg.OMSVGPathElement;
import org.vectomatic.dom.svg.OMSVGRectElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.OMSVGStopElement;
import org.vectomatic.dom.svg.OMSVGTextElement;
import org.vectomatic.dom.svg.itf.ISVGUnitTypes;
import org.vectomatic.dom.svg.ui.SVGImage;
import org.vectomatic.dom.svg.utils.OMSVGParser;
import org.vectomatic.dom.svg.utils.SVGConstants;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDrawOptions;
import com.google.gwt.visualization.client.AbstractVisualization;

public class OdometerVisualization extends AbstractVisualization<OdometerVisualization.OdometerVisualizationDrawOptions> {

    private static final String ID_COLORED_GRADIENT = "colored_gradient";
    private static final String ID_ITEM_GRADIENT = "item_gradient";
    
    private static final double ITEM_SIZE = 25.0d;
    private static final double ROUND_XY = ITEM_SIZE / 5d;
    private static final double ROUND_ARG = ITEM_SIZE / 15d;
    private static final double SEPARATOR_SIZE = 1.0d;
    private static final String SEPARATOR_COLOR = "#F7F7F7";
    private static final double TEXT_X_OFFSET = ITEM_SIZE / 3.3d;
    private static final double TEXT_Y_OFFSET = ITEM_SIZE / 1.39d;
    private static final double TEXT_SIZE = ITEM_SIZE / 1.47d;
    private static final String TEXT_COLOR = "#FFFFFF";
    
    private static final NumberFormat DECIMAL_FORMAT = NumberFormat.getFormat("#0.##");


    private OMSVGSVGElement svg;
    private OMSVGDocument doc;

    public OdometerVisualization() {
        this.doc = OMSVGParser.createDocument();
        this.svg = this.doc.createSVGSVGElement();
        svg.setAttribute(SVGConstants.SVG_VERSION_ATTRIBUTE, "1.1");

        SVGImage svgImage = new SVGImage(this.svg);
        initWidget(svgImage);
    }

    @Override
    public void draw(AbstractDataTable data, OdometerVisualizationDrawOptions options) {

        svg.setWidth(OMSVGLength.SVG_LENGTHTYPE_PERCENTAGE, 100.0f);
        svg.setHeight(OMSVGLength.SVG_LENGTHTYPE_PERCENTAGE, 100.0f);
        svg.setId("odometer");
        svg.appendChild(this.createDefs(options));
        
        if (data.getNumberOfRows() <= 0 || data.getNumberOfColumns() <= 0) {
            return;
        }
        svg.appendChild(this.createLeftElement());
        svg.appendChild(this.createText(data.getFormattedValue(0, 0), 0));
        
        if (data.getNumberOfColumns() > 2) {
            for(int i=1;i<data.getNumberOfColumns() - 1;i++) {
                svg.appendChild(this.createSeparator(i - 1));
                svg.appendChild(this.createItem(i - 1));
                svg.appendChild(this.createText(data.getFormattedValue(0, i), i));
            }
        }
        
        if (data.getNumberOfColumns() > 1) {
            svg.appendChild(this.createSeparator(data.getNumberOfColumns() - 2));
            svg.appendChild(this.createRightElement(data.getNumberOfColumns()));
            svg.appendChild(this.createText(data.getFormattedValue(0, data.getNumberOfColumns() - 1), data.getNumberOfColumns() - 1));
        }
        

    }

    private OMSVGDefsElement createDefs(OdometerVisualizationDrawOptions options) {
        OMSVGDefsElement ret = this.doc.createSVGDefsElement();

        OMSVGLinearGradientElement coloredGradient = this.doc.createSVGLinearGradientElement();
        coloredGradient.setId(ID_COLORED_GRADIENT);
        coloredGradient.setAttribute(SVGConstants.SVG_X1_ATTRIBUTE, "0%");
        coloredGradient.setAttribute(SVGConstants.SVG_Y1_ATTRIBUTE, "-10%");
        coloredGradient.setAttribute(SVGConstants.SVG_X2_ATTRIBUTE, "0%");
        coloredGradient.setAttribute(SVGConstants.SVG_Y2_ATTRIBUTE, "60%");
        
        OMSVGStopElement coloredGradientStop1 = this.doc.createSVGStopElement();
        coloredGradientStop1.setAttribute(SVGConstants.SVG_OFFSET_ATTRIBUTE, "0%");
        coloredGradientStop1.setAttribute(SVGConstants.SVG_STYLE_ATTRIBUTE, "stop-color:#FFFFFF;stop-opacity:1" );
        coloredGradient.appendChild(coloredGradientStop1);
        
        OMSVGStopElement coloredGradientStop2 = this.doc.createSVGStopElement();
        coloredGradientStop2.setAttribute(SVGConstants.SVG_OFFSET_ATTRIBUTE, "100%");
        coloredGradientStop2.setAttribute(SVGConstants.SVG_STYLE_ATTRIBUTE, "stop-color:" + options.getSpecialColor() + ";stop-opacity:1" );
        coloredGradient.appendChild(coloredGradientStop2);
        
        ret.appendChild(coloredGradient);
        
        OMSVGLinearGradientElement itemGradient = this.doc.createSVGLinearGradientElement();
        itemGradient.setId(ID_ITEM_GRADIENT);
        itemGradient.setAttribute(SVGConstants.SVG_X1_ATTRIBUTE, "0%");
        itemGradient.setAttribute(SVGConstants.SVG_Y1_ATTRIBUTE, "-10%");
        itemGradient.setAttribute(SVGConstants.SVG_X2_ATTRIBUTE, "0%");
        itemGradient.setAttribute(SVGConstants.SVG_Y2_ATTRIBUTE, "60%");
        
        OMSVGStopElement itemGradientStop1 = this.doc.createSVGStopElement();
        itemGradientStop1.setAttribute(SVGConstants.SVG_OFFSET_ATTRIBUTE, "0%");
        itemGradientStop1.setAttribute(SVGConstants.SVG_STYLE_ATTRIBUTE, "stop-color:#FFFFFF;stop-opacity:1" );
        itemGradient.appendChild(itemGradientStop1);
        
        OMSVGStopElement itemGradientStop2 = this.doc.createSVGStopElement();
        itemGradientStop2.setAttribute(SVGConstants.SVG_OFFSET_ATTRIBUTE, "100%");
        itemGradientStop2.setAttribute(SVGConstants.SVG_STYLE_ATTRIBUTE, "stop-color:" + options.getItemColor() + ";stop-opacity:1" );
        itemGradient.appendChild(itemGradientStop2);
        
        ret.appendChild(itemGradient);
        
        return ret;
    }
    
    private OMSVGPathElement createLeftElement() {
        OMSVGPathElement ret = this.doc.createSVGPathElement();
        
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
        
        ret.setAttribute(SVGConstants.SVG_D_ATTRIBUTE, d.toString());
        ret.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, "url(#" + ID_ITEM_GRADIENT + ")");
        
        return ret;
    }
    
    private OMSVGPathElement createRightElement(int totalItems) {
        OMSVGPathElement ret = this.doc.createSVGPathElement();
        
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
        
        ret.setAttribute(SVGConstants.SVG_D_ATTRIBUTE, d.toString());
        ret.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, "url(#" + ID_COLORED_GRADIENT + ")");
        
        return ret;
    }
    
    private OMSVGRectElement createSeparator(int separatorIndex) {
        OMSVGRectElement ret = this.doc.createSVGRectElement();
        
        double x = (separatorIndex * SEPARATOR_SIZE) + ((separatorIndex + 1) * ITEM_SIZE);
        
        ret.setAttribute(SVGConstants.SVG_X_ATTRIBUTE, DECIMAL_FORMAT.format(x));
        ret.setAttribute(SVGConstants.SVG_Y_ATTRIBUTE, "0");
        ret.setAttribute(SVGConstants.SVG_WIDTH_ATTRIBUTE, DECIMAL_FORMAT.format(SEPARATOR_SIZE));
        ret.setAttribute(SVGConstants.SVG_HEIGHT_ATTRIBUTE, DECIMAL_FORMAT.format(ITEM_SIZE));
        
        ret.setAttribute(SVGConstants.SVG_FILL_ATTRIBUTE, SEPARATOR_COLOR);
        
        return ret;
    }
    
    private OMSVGRectElement createItem(int itemIndex) {
        OMSVGRectElement ret = this.doc.createSVGRectElement();
        
        double x = (itemIndex + 1) * (SEPARATOR_SIZE + ITEM_SIZE);
        
        ret.setAttribute(SVGConstants.SVG_X_ATTRIBUTE, DECIMAL_FORMAT.format(x));
        ret.setAttribute(SVGConstants.SVG_Y_ATTRIBUTE, "0");
        ret.setAttribute(SVGConstants.SVG_WIDTH_ATTRIBUTE, DECIMAL_FORMAT.format(ITEM_SIZE));
        ret.setAttribute(SVGConstants.SVG_HEIGHT_ATTRIBUTE, DECIMAL_FORMAT.format(ITEM_SIZE));
        
        ret.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, "url(#" + ID_ITEM_GRADIENT + ")");
        
        return ret;
    }
    
    private OMSVGTextElement createText(String value, int textIndex) {
        double x = (textIndex) * (ITEM_SIZE + SEPARATOR_SIZE) + TEXT_X_OFFSET;
        OMSVGTextElement ret = this.doc.createSVGTextElement(Double.valueOf(x).floatValue(), Double.valueOf(TEXT_Y_OFFSET).floatValue(), ISVGUnitTypes.SVG_UNIT_TYPE_USERSPACEONUSE, value);
        
        ret.setAttribute(SVGConstants.SVG_FONT_SIZE_ATTRIBUTE, DECIMAL_FORMAT.format(TEXT_SIZE));
        ret.setAttribute(SVGConstants.SVG_FONT_WEIGHT_ATTRIBUTE, "bold");
        ret.setAttribute(SVGConstants.SVG_FONT_FAMILY_ATTRIBUTE, "Arial");
        ret.setAttribute(SVGConstants.SVG_FILL_ATTRIBUTE, TEXT_COLOR);
        
        
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
    }

}
