package io.rcardin.hexagonal.portfolio

data class Portfolio(val name: String) {

    private val stocks: List<Stock> = mutableListOf()

    fun getTotalValue() = stocks.sumByDouble { it.getTotalValue() }
}

data class Stock(val name: String, val owned: Long, val value: Double) {
    fun getTotalValue(): Double = value * owned
}