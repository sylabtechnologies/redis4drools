package jollyjoe

class OrderItem {
    Integer quantity
    Float totalAmount

    static belongsTo = [order: OnlineOrder, product: Product]

    static constraints = {
        quantity blank: false, nullable: false, min: 1
        totalAmount nullable: true, min: 0.0F
        order blank: false, nullable: false
    }
}
