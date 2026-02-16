package rewards

class InventoryController {

    List<Product> products = [
        new Product(name: "breakfast blend", sku: "BB-1"),
        new Product(name: "dark roast", sku: "DR-2") ]

    def index() {
        [products: products]
    }

    def edit(Integer id) {
        def product = products[id]
        [product: product, id: id]

        // def myproduct = new Product( name: "breakfast blend", sku: "BB-1")
        /// MAKE A MAP!
        // [product: myproduct]
    }

    def update(Integer id) {
        def product = products[id]
        product.name = params.name
        product.sku = params.sku
        redirect(action: "index")
    }

    def create() {
        // Show a blank Product form
        [product: new Product()]
    }

    def save() {
        // Create a new Product from form params
        def p = new Product(name: params.name, sku: params.sku)

        // Add it to the in-memory list
        products << p

        // System.out.println("Saved product: ${p.name} (${p.sku})")
        println "Current products:"
        products.eachWithIndex { prod, i ->
            println "  #${i}: ${prod.name} (${prod.sku})"
        }
        // Go back to the list
        redirect(action: "index")
    }

}
