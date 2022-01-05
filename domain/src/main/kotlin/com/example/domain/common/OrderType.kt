package com.example.domain.common

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}