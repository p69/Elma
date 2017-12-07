package com.example.pavelshilyagov.tryelmish

infix fun <X,Y,Z> ( (X) -> Y).then(g: (Y) -> Z):  (X) -> Z = { x -> g(this(x)) }