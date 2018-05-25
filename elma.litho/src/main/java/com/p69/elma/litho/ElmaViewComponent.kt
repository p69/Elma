package com.p69.elma.litho

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
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
        fun onCreateLayout(c: ComponentContext, @Prop layout: ElmaLithoView.Layout): Component {
            return layout.builder.build()
        }
    }

}

@LayoutSpec
class ElmaViewComponentSpec {

    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(c: ComponentContext, @Prop viewCreator: ElmaViewCreator): Component {
            return when(viewCreator) {
                is ElmaViewCreator.Some -> {
                    val view = viewCreator.createView()
                    when (view) {
                        is ElmaLithoView.Widget -> view.builder.build()
                        is ElmaLithoView.Layout -> view.builder.build()
                    }
                }
                is ElmaViewCreator.None -> Text.create(c).text("None").build()
            }
        }
    }

}