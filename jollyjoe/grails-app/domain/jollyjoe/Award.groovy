package jollyjoe

class Award {
    Date awardDate
    String type
    Integer points

    static belongsTo = [customer: Customer]

    static constraints = {
        type(inList: ['PURCHASE', 'REWARD'], blank: false, nullable: false)
        points blank: false, nullable: false, min: 0
        awardDate blank: false, nullable: false
    }
}
