package it.bottai.model;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;

class FontFilter extends TextRegionEventFilter {
    protected FontFilter(Rectangle filterRect) {
        super(filterRect);
    }

    @Override
    public boolean accept(IEventData data, EventType type) {
        if (type.equals(EventType.RENDER_TEXT)) {
            TextRenderInfo renderInfo = (TextRenderInfo) data;

            PdfFont font = renderInfo.getFont();
            if (null != font) {
                String fontName = font.getFontProgram().getFontNames().getFontName();
                return fontName.endsWith("Bold") || fontName.endsWith("Oblique");
            }
        }
        return false;
    }
}

