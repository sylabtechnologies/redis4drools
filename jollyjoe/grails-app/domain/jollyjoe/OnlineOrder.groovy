package jollyjoe

class OnlineOrder {
    Date orderDate
    Long orderNumber
    Float totalAmount

    static hasMany = [orderItems: OrderItem]
    static belongsTo = [customer: Customer]

    static constraints = {
        orderDate blank: false, nullable: false
        orderNumber blank: false, nullable: false, unique: true
        totalAmount nullable: true, min: 0.0F
        customer blank: false, nullable: false
    }
}
