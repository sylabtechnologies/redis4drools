package jollyjoe

class Product {
    String name
    String sku
    Float price

    static hasMany = [orderItems: OrderItem]

    static constraints = {
        name blank: false, nullable: false
        sku blank: false, nullable: false, unique: true
        price nullable: false, min: 0.0F
    }
}
