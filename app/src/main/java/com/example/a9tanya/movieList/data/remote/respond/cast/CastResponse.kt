package com.example.a9tanya.movieList.data.remote.respond.cast

data class CastResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)