package jollyjoe

class Customer {
    String firstName
    String lastName
    String email
    Long phone
    Integer awardPoints

    static hasMany = [onlineOrders: OnlineOrder, awards: Award]

    static constraints = {
        phone(blank: false, nullable: false)
        firstName blank: false, nullable: false
        lastName blank: false, nullable: false
        email blank: false, nullable: false, unique: true
        awardPoints nullable: false, min: 0
    }
}
