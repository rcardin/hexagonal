package io.rcardin.hexagonal.portfolio.domain

data class Portfolio(val name: String, val stocks: Set<Stock> = setOf()) {

    fun buy(name: String, quantity: Long): Portfolio {
        val maybeStock = stocks.firstOrNull { it.name == name }
        val newStocks = stocks.plus(maybeStock?.add(quantity) ?: Stock(name, quantity))
        return copy(stocks = newStocks)
    }

    fun getStock(name: String): Stock? = stocks.firstOrNull { it.name == name }
}

data class Stock(val name: String, val owned: Long) {
    fun add(quantity: Long): Stock = copy(owned = owned + quantity)
}
