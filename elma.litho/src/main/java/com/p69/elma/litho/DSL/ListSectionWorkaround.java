package com.p69.elma.litho.DSL;


import com.facebook.litho.ComponentContext;
import com.facebook.litho.sections.Section;
import com.facebook.litho.sections.SectionContext;

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

    public Section.Builder<?, ?> getBuilder() {
        return mBuilder;
    }

    public ComponentContext getComponentContext() {
        return mComponentContext;
    }
}
