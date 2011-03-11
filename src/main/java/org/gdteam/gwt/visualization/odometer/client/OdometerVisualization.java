package org.gdteam.gwt.visualization.odometer.client;

import org.vectomatic.dom.svg.OMSVGDefsElement;
import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGGElement;
import org.vectomatic.dom.svg.OMSVGLength;
import org.vectomatic.dom.svg.OMSVGLinearGradientElement;
import org.vectomatic.dom.svg.OMSVGPathElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.OMSVGStopElement;
import org.vectomatic.dom.svg.OMSVGTextElement;
import org.vectomatic.dom.svg.itf.ISVGUnitTypes;
import org.vectomatic.dom.svg.ui.SVGImage;
import org.vectomatic.dom.svg.utils.OMSVGParser;
import org.vectomatic.dom.svg.utils.SVGConstants;

import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDrawOptions;
import com.google.gwt.visualization.client.AbstractVisualization;

public class OdometerVisualization extends AbstractVisualization<OdometerVisualization.OdometerVisualizationDrawOptions> {

    private static final String URI_XLINK = "http://www.w3.org/1999/xlink";

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

        svg.setWidth(OMSVGLength.SVG_LENGTHTYPE_PX, 150);
        svg.setHeight(OMSVGLength.SVG_LENGTHTYPE_PX, 23);
        svg.setId("odometer");
        svg.appendChild(this.createDefs(options));

        OMSVGGElement content = this.doc.createSVGGElement();
        svg.appendChild(content);
        content.setId("content");
        content.setAttribute(SVGConstants.SVG_TRANSFORM_ATTRIBUTE, "matrix(0.35902946,0,0,0.35902946,11.643827,62.282265)");

        content.appendChild(this.createLeftElement("odometer_0", "leftlinear"));

        content.appendChild(this
                .createSimpleElement(
                        "odometer_1",
                        "linear1",
                        "m 27.357013,-171.84367 0,61.71871 20.53125,0 c 0.0521,6.7e-4 0.10395,0 0.15625,0 l 10.281251,0 15.343762,0 12.750008,0 0,-13.74999 0,-34.21873 0,-13.74999 -12.750008,0 -15.343762,0 -10.281251,0 c -0.0524,0 -0.10395,-6.7e-4 -0.15625,0 l -20.53125,0 z"));
        content.appendChild(this
                .createSimpleElement(
                        "odometer_2",
                        "linear2",
                        "m 87.145444,-171.84367 0,61.71871 20.531266,0 c 0.0521,6.7e-4 0.10395,0 0.15625,0 l 10.28125,0 15.34375,0 12.75,0 0,-13.74999 0,-34.21873 0,-13.74999 -12.75,0 -15.34375,0 -10.28125,0 c -0.0524,0 -0.10395,-6.7e-4 -0.15625,0 l -20.531266,0 z"));
        content.appendChild(this
                .createSimpleElement(
                        "odometer_3",
                        "linear3",
                        "m 146.93388,-171.84367 0,61.71871 20.53125,0 c 0.0521,6.7e-4 0.10395,0 0.15625,0 l 10.28125,0 15.34375,0 12.75,0 0,-13.74999 0,-34.21873 0,-13.74999 -12.75,0 -15.34375,0 -10.28125,0 c -0.0524,0 -0.10395,-6.7e-4 -0.15625,0 l -20.53125,0 z"));
        content.appendChild(this
                .createSimpleElement(
                        "odometer_4",
                        "linear4",
                        "m 206.7223,-171.84367 0,61.71871 20.53125,0 c 0.0521,6.7e-4 0.10395,0 0.15625,0 l 10.28125,0 15.34375,0 12.75,0 0,-13.74999 0,-34.21873 0,-13.74999 -12.75,0 -15.34375,0 -10.28125,0 c -0.0524,0 -0.10395,-6.7e-4 -0.15625,0 l -20.53125,0 z"));
        content.appendChild(this
                .createSimpleElement(
                        "odometer_5",
                        "linear5",
                        "m 266.51073,-171.84367 0,61.71871 20.53125,0 c 0.0521,6.7e-4 0.10395,0 0.15625,0 l 10.28125,0 15.34375,0 12.75,0 0,-13.74999 0,-34.21873 0,-13.74999 -12.75,0 -15.34375,0 -10.28125,0 c -0.0524,0 -0.10395,-6.7e-4 -0.15625,0 l -20.53125,0 z"));

        OMSVGPathElement path = this.createRightElement("odometer6", "rigtlinear");

        content.appendChild(path);

        // Only works with one row
        String dValue = data.getFormattedValue(0, 0);
        String[] values = new String[7];

        for (int i = 0; i < values.length; i++) {
            if (dValue.length() - 7 + i < 0) {
                values[i] = "";
            } else {
                values[i] = String.valueOf(dValue.charAt(dValue.length() - 7 + i));
            }
        }

        float xStart = -13.51538f;
        for (int i = 0; i < values.length; i++) {
            content.appendChild(this.createTextElement(values[i], xStart + (i * 59.788409f)));
        }
    }

    private OMSVGDefsElement createDefs(OdometerVisualizationDrawOptions options) {
        OMSVGDefsElement ret = this.doc.createSVGDefsElement();

        ret.appendChild(this.createSimpleGradient("specialcolorlinear", options.getSpecialColor()));
        ret.appendChild(this.createSimpleGradient("itemcolorlinear", options.getItemColor()));
        ret.appendChild(this.createGradient("rigtlinear", "specialcolorlinear", "matrix(-1,0,0,1,567.29915,-345.78107)"));
        ret.appendChild(this.createGradient("linear1", "itemcolorlinear", "matrix(-1,0,0,1,268.36023,-345.78848)"));
        ret.appendChild(this.createGradient("linear2", "itemcolorlinear", "matrix(-1,0,0,1,328.14864,-345.78848)"));
        ret.appendChild(this.createGradient("linear3", "itemcolorlinear", "matrix(-1,0,0,1,387.93706,-345.78848)"));
        ret.appendChild(this.createGradient("linear4", "itemcolorlinear", "matrix(-1,0,0,1,447.72548,-345.78848)"));
        ret.appendChild(this.createGradient("linear5", "itemcolorlinear", "matrix(-1,0,0,1,507.51391,-345.78848)"));
        ret.appendChild(this.createGradient("leftlinear", "itemcolorlinear", "translate(-163.77157,-138.48786)"));
        return ret;
    }

    private OMSVGLinearGradientElement createSimpleGradient(String id, String color) {
        OMSVGLinearGradientElement ret = this.doc.createSVGLinearGradientElement();
        ret.setId(id);
        OMSVGStopElement stop1 = this.doc.createSVGStopElement();
        ret.appendChild(stop1);
        stop1.setId(id + "_stop1");
        stop1.setAttribute(SVGConstants.SVG_OFFSET_ATTRIBUTE, "0");
        stop1.getStyle().setSVGProperty(SVGConstants.CSS_STOP_COLOR_PROPERTY, color);
        stop1.getStyle().setSVGProperty(SVGConstants.CSS_STOP_OPACITY_PROPERTY, "1");

        OMSVGStopElement stop2 = this.doc.createSVGStopElement();
        ret.appendChild(stop2);
        stop2.setId(id + "_stop2");
        stop2.setAttribute(SVGConstants.SVG_OFFSET_ATTRIBUTE, "1");
        stop2.getStyle().setSVGProperty(SVGConstants.CSS_STOP_COLOR_PROPERTY, "#F7F7F7");
        stop2.getStyle().setSVGProperty(SVGConstants.CSS_STOP_OPACITY_PROPERTY, "1");

        return ret;
    }

    private OMSVGLinearGradientElement createGradient(String id, String ref, String gradientTransform) {
        OMSVGLinearGradientElement ret = this.doc.createSVGLinearGradientElement();
        ret.setId(id);
        ret.setAttributeNS(URI_XLINK, "href", "#" + ref);
        ret.setAttribute(SVGConstants.SVG_GRADIENT_UNITS_ATTRIBUTE, "userSpaceOnUse");
        ret.setAttribute(SVGConstants.SVG_GRADIENT_TRANSFORM_ATTRIBUTE, gradientTransform);
        ret.setAttribute(SVGConstants.SVG_X1_ATTRIBUTE, "208.64801");
        ret.setAttribute(SVGConstants.SVG_Y1_ATTRIBUTE, "235.37457");
        ret.setAttribute(SVGConstants.SVG_X2_ATTRIBUTE, "208.52249");
        ret.setAttribute(SVGConstants.SVG_Y2_ATTRIBUTE, "155.22429");

        return ret;
    }

    private OMSVGGElement createLeftElement(String id, String gradientRef) {
        OMSVGGElement ret = this.doc.createSVGGElement();
        ret.setId(id);
        ret.setAttribute(SVGConstants.SVG_TRANSFORM_ATTRIBUTE, "translate(-50.597329,-207.29328)");

        OMSVGPathElement path = this.doc.createSVGPathElement();
        ret.appendChild(path);

        path.setId(id + "_path");
        path.setAttribute(
                SVGConstants.SVG_D_ATTRIBUTE,
                "m 30.915932,35.449639 c -7.06227,0 -12.75,6.13253 -12.75,13.75 l 0,34.21875 c 0,7.61747 5.68773,13.75 12.75,13.75 l 25.625001,0 c 0.0523,0 0.10412,6.7e-4 0.15625,0 l 20.53125,0 0,-61.71875 -20.53125,0 c -0.0523,-6.7e-4 -0.10382,0 -0.15625,0 l -25.625001,0 z");
        path.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, "url(#" + gradientRef + ")");
        path.getStyle().setSVGProperty(SVGConstants.CSS_FILL_OPACITY_PROPERTY, "1");
        path.getStyle().setSVGProperty(SVGConstants.CSS_FILL_RULE_PROPERTY, "nonzero");
        path.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_PROPERTY, "none");

        return ret;
    }

    private OMSVGPathElement createRightElement(String id, String gradientRef) {
        OMSVGPathElement path = this.doc.createSVGPathElement();

        path.setId(id);
        path.setAttribute(
                SVGConstants.SVG_D_ATTRIBUTE,
                "m 372.61165,-171.84367 c 7.06227,0 12.75,6.13253 12.75,13.74999 l 0,34.21873 c 0,7.61746 -5.68773,13.74999 -12.75,13.74999 l -25.625,0 c -0.0523,0 -0.10412,6.7e-4 -0.15625,0 l -20.53125,0 0,-61.71871 20.53125,0 c 0.0523,-6.7e-4 0.10382,0 0.15625,0 l 25.625,0 z");
        path.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, "url(#" + gradientRef + ")");
        path.getStyle().setSVGProperty(SVGConstants.CSS_FILL_OPACITY_PROPERTY, "1");
        path.getStyle().setSVGProperty(SVGConstants.CSS_FILL_RULE_PROPERTY, "nonzero");
        path.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_PROPERTY, "none");

        return path;
    }

    private OMSVGPathElement createSimpleElement(String id, String gradientRef, String d) {
        OMSVGPathElement path = this.doc.createSVGPathElement();

        path.setId(id);
        path.setAttribute(SVGConstants.SVG_D_ATTRIBUTE, d);

        path.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, "url(#" + gradientRef + ")");
        path.getStyle().setSVGProperty(SVGConstants.CSS_FILL_OPACITY_PROPERTY, "1");
        path.getStyle().setSVGProperty(SVGConstants.CSS_FILL_RULE_PROPERTY, "nonzero");
        path.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_PROPERTY, "none");

        return path;
    }

    private OMSVGTextElement createTextElement(String value, float x) {
        OMSVGTextElement ret = this.doc.createSVGTextElement(x, -126.60928f, ISVGUnitTypes.SVG_UNIT_TYPE_USERSPACEONUSE, value);

        ret.getStyle().setSVGProperty(SVGConstants.CSS_FONT_SIZE_PROPERTY, "40px");
        ret.getStyle().setSVGProperty(SVGConstants.CSS_FONT_STYLE_PROPERTY, "normal");
        ret.getStyle().setSVGProperty(SVGConstants.CSS_FONT_VARIANT_PROPERTY, "normal");
        ret.getStyle().setSVGProperty(SVGConstants.CSS_FONT_WEIGHT_PROPERTY, "bold");
        ret.getStyle().setSVGProperty(SVGConstants.CSS_FONT_FAMILY_PROPERTY, "Arial");
        ret.getStyle().setSVGProperty(SVGConstants.CSS_LINE_HEIGHT_PROPERTY, "125%");
        ret.getStyle().setSVGProperty(SVGConstants.CSS_LETTER_SPACING_PROPERTY, "0px");
        ret.getStyle().setSVGProperty(SVGConstants.CSS_WORD_SPACING_PROPERTY, "0px");
        ret.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, "#ffffff");
        ret.getStyle().setSVGProperty(SVGConstants.CSS_FILL_OPACITY_PROPERTY, "1");
        ret.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_PROPERTY, "none");

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
