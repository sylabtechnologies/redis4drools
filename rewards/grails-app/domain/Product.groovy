package rewards

class Product {

    String name
    String sku

    static constraints = {
        name blank: false
        sku unique: true, blank: false
    }
}