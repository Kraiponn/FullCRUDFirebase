package com.ksntechnology.fullcrudfirebase;

import java.util.List;

public class VertItem {
    private String textSection;
    private List<HozItem> horizontalItem;

    public VertItem(String textSection, List<HozItem> horizontalItem) {
        this.textSection = textSection;
        this.horizontalItem = horizontalItem;
    }

    public String getTextSection() {
        return textSection;
    }

    public void setTextSection(String textSection) {
        this.textSection = textSection;
    }

    public List<HozItem> getHorizontalItem() {
        return horizontalItem;
    }

    public void setHorizontalItem(List<HozItem> horizontalItem) {
        this.horizontalItem = horizontalItem;
    }

}
