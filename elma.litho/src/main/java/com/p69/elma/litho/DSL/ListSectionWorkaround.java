package com.p69.elma.litho.DSL;


import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.widget.RecyclerCollectionComponent;

public class ListSectionWorkaround {
    private final SectionContext mSectionContext;
    private ComponentContext mComponentContext;
    private final ListSection.Builder mBuilder;

    public ListSectionWorkaround(ComponentContext componentContext) {
        mSectionContext = new SectionContext(componentContext);
        mComponentContext = componentContext;
        mBuilder = ListSection.create(mSectionContext);
    }

    public ListSectionWorkaround data (Container data) {
        mBuilder.data(data);
        return this;
    }

    public Component.Builder<?, ?> buildRecyclerCollection() {
        return RecyclerCollectionComponent.create(mSectionContext).disablePTR(true).section(mBuilder.build());
    }

    public ComponentContext getComponentContext() {
        return mComponentContext;
    }
}
