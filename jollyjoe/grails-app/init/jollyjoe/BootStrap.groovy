package jollyjoe

class BootStrap {

    def init = { servletContext ->
        Product.withTransaction {   // wrap in transaction block
            if (Product.count() == 0) {
                new Product(name: "Breakfast Blend", sku: "BB-01", price: 9.99).save(flush: true)
                new Product(name: "Espresso", sku: "ES-01", price: 11.99).save(flush: true)
            }
        }

        Customer.withTransaction {
            if (Customer.count() == 0) {
                new Customer(firstName: "Bo", lastName: "Didley", email: "bo.didley@example.com", phone: 1234567890, awardPoints: 1).save(flush: true)
                new Customer(firstName: "Jane", lastName: "Smith", email: "jane.smith@example.com", phone: 2345678901, awardPoints: 1).save(flush: true)
                new Customer(firstName: "Bill", lastName: "Dana", email: "bill.dana@example.com", phone: 3456789012, awardPoints: 1).save(flush: true)

                new Customer(firstName: "Alice", lastName: "Johnson", email: "alice.johnson@example.com", phone: 4567890123, awardPoints: 1).save(flush: true)
                new Customer(firstName: "Bob", lastName: "Williams", email: "bob.williams@example.com", phone: 5678901234, awardPoints: 1).save(flush: true)
                new Customer(firstName: "Carol", lastName: "Brown", email: "carol.brown@example.com", phone: 6789012345, awardPoints: 1).save(flush: true)
                new Customer(firstName: "David", lastName: "Miller", email: "david.miller@example.com", phone: 7890123456, awardPoints: 1).save(flush: true)
                new Customer(firstName: "Eve", lastName: "Wilson", email: "eve.wilson@example.com", phone: 8901234567, awardPoints: 1).save(flush: true)
                new Customer(firstName: "Frank", lastName: "Moore", email: "frank.moore@example.com", phone: 9012345678, awardPoints: 1).save(flush: true)
                new Customer(firstName: "Grace", lastName: "Taylor", email: "grace.taylor@example.com", phone: 1123456789, awardPoints: 1).save(flush: true)
                new Customer(firstName: "Hank", lastName: "Anderson", email: "hank.anderson@example.com", phone: 2234567890, awardPoints: 1).save(flush: true)
                new Customer(firstName: "Ivy", lastName: "Thomas", email: "ivy.thomas@example.com", phone: 3345678901, awardPoints: 1).save(flush: true)
                new Customer(firstName: "Jack", lastName: "Jackson", email: "jack.jackson@example.com", phone: 4456789012, awardPoints: 1).save(flush: true)
            }
        }
    }
    def destroy = {
    }
}
