package com.p69.elma.litho.DSL

import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.SingleComponentSection
import com.facebook.litho.widget.Text


@GroupSectionSpec
class ListSectionSpec {
    companion object {
        @JvmStatic
        @OnCreateChildren
        fun onCreateChildren(c: SectionContext): Children {
            val builder = Children.create()

            for (i in 0..31) {
                builder.child(
                        SingleComponentSection.create(c)
                                .key(i.toString())
                                .component(Text.create(c).text(i.toString()).build()))
            }
            return builder.build()
        }
    }
}

