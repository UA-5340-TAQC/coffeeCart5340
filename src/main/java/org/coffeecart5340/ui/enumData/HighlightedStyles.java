package org.coffeecart5340.ui.enumData;

public enum HighlightedStyles {
    GOLDEN("rgb(250, 235, 215)"),
    GOLDEN_ROD("rgb(218, 165, 32)");

    private final String style;
    HighlightedStyles(String style){
        this.style = style;
    }
    public String getStyle(){
        return style;
    }

    @Override
    public String toString(){
        return style;
    }
}
