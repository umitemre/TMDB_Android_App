package com.example.mobilliumcase.model

import java.util.ArrayList

data class ApiResponse(
    val dates: Dates,
    val page: Int,
    val results: ArrayList<Result>,
    val total_pages: Int,
    val total_results: Int
)