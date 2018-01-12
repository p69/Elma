package com.p69.elma.litho

import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.SingleComponentSection
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.widget.Text

sealed class ElmaViewCreator {
    object None : ElmaViewCreator()
    data class Some(val createView: () -> ElmaLithoView) : ElmaViewCreator()
}

@LayoutSpec
class ElmaLayoutViewComponentSpec {

    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext, @Prop layout: ElmaLithoView.Layout): ComponentLayout {
            return layout.builder.build()
        }
    }

}

@LayoutSpec
class ElmaViewComponentSpec {

    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext, @Prop viewCreator: ElmaViewCreator): ComponentLayout {
            val recycleCollectionBuilder = RecyclerCollectionComponent.create(c)
            when(viewCreator) {
                is ElmaViewCreator.Some -> {
                    val view = viewCreator.createView()
                    when (view) {
                        is ElmaLithoView.Widget -> recycleCollectionBuilder.section(SingleComponentSection.create(SectionContext(c)).component(view.builder))
                        is ElmaLithoView.Layout -> recycleCollectionBuilder.section(SingleComponentSection.create(SectionContext(c)).component(ElmaLayoutViewComponent.create(c).layout(view)))
                        is ElmaLithoView.Section -> recycleCollectionBuilder.section(view.builder)
                    }
                }
                is ElmaViewCreator.None -> {recycleCollectionBuilder.section(SingleComponentSection.create(SectionContext(c)).component(Text.create(c).text("None")))}
            }
            return Column.create(c).child(recycleCollectionBuilder).build()
        }
    }

}

@GroupSectionSpec
class ElmaViewSectionSpec {
    companion object {
        @JvmStatic
        @OnCreateChildren
        fun onCreateChildren(c: SectionContext, @Prop viewCreator: ElmaViewCreator): Children {
            val builder = Children.create()
            when (viewCreator) {
                is ElmaViewCreator.Some -> {
                    val view = viewCreator.createView()
                    when (view) {
                        is ElmaLithoView.Widget -> builder.child(SingleComponentSection.create(SectionContext(c)).component(view.builder))
                        is ElmaLithoView.Layout -> builder.child(SingleComponentSection.create(SectionContext(c)).component(ElmaLayoutViewComponent.create(c).layout(view)))
                        is ElmaLithoView.Section -> builder.child(view.builder)
                    }
                }
                is ElmaViewCreator.None -> {
                    builder.child(SingleComponentSection.create(SectionContext(c)).component(Text.create(c).text("None")))
                }
            }
            return builder.build()
        }


    }
}

@LayoutSpec
class SimpleElmaViewComponentSpec {

    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext, @Prop viewCreator: ElmaViewCreator): ComponentLayout {
            val builder = Column.create(c)
            when(viewCreator) {
                is ElmaViewCreator.Some -> {
                    val view = viewCreator.createView()
                    when (view) {
                        is ElmaLithoView.Widget -> builder.child(view.builder)
                        is ElmaLithoView.Layout -> builder.child(view.builder)
                        is ElmaLithoView.Section -> builder.child(RecyclerCollectionComponent.create(c).disablePTR(true).section(view.builder))
                    }
                }
                is ElmaViewCreator.None -> {builder.child((Text.create(c).text("None")))}
            }
            return builder.build()
        }
    }

}