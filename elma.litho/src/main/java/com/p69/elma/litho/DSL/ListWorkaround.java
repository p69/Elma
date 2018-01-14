package com.p69.elma.litho.DSL;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.p69.elma.litho.ElmaLithoView;

import java.util.List;

/*
Kotlin compiler breaks because of ListComponent.Builder (inner class with generics).
Added this java-class wrapper to makeKotlin compiler happy
 */
public class ListWorkaround {
    private ComponentContext mComponentContext;
    private final ListComponent.Builder mBuilder;

    public ListWorkaround(ComponentContext componentContext) {
        mComponentContext = componentContext;
        mBuilder = ListComponent.create(mComponentContext);
    }

    public ListWorkaround items(List<ElmaLithoView> items) {
        mBuilder.items(items);
        return this;
    }

    public Component.Builder<?, ?> getBuilder() {
        return mBuilder;
    }

    public ComponentContext getComponentContext() {
        return mComponentContext;
    }
}
