package jollyjoe

import grails.gorm.transactions.Transactional

@Transactional
class CustomerService {
        /**
         * Updates a customer's profile by phone number using direct SQL update.
         * Returns a map: [success: boolean, message: String, customer: Customer]
         */
        def updateByPhone(Long phone, String firstName, String lastName, String email) {
            if (!firstName || !lastName || !email) {
                return [success: false, message: "First name, last name, and email are required.", customer: null]
            }
            def customer = Customer.findByPhone(phone)
            if (!customer) {
                return [success: false, message: "Customer not found", customer: null]
            }
            // Use SQL update for performance
            def updated = Customer.executeUpdate(
                "update Customer set firstName = :firstName, lastName = :lastName, email = :email where phone = :phone",
                [firstName: firstName, lastName: lastName, email: email, phone: phone]
            )
            if (updated == 0) {
                return [success: false, message: "Update failed", customer: customer]
            }
            // Refresh customer instance
            customer.refresh()
            return [success: true, message: "Profile updated", customer: customer]
        }
    /**
     * Looks up a customer by phone. If not found, create a new Customer with the given phone.
     * Returns a tuple
     */
    def lookup(Long phone) {
        def processedCustomer = Customer.findByPhone(phone)
        def welcome = ""
        def points = "1 point."
        if (!processedCustomer) {
            processedCustomer = new Customer()
            processedCustomer.phone = phone
            processedCustomer.firstName = "New Customer"
            processedCustomer.lastName = "Smith"
            processedCustomer.email = phone + "@youremail.com" // try unique values
            processedCustomer.awardPoints = 1
            processedCustomer.save(flush: true) // get the ID for the customer

            println "Creating new customer with phone: $phone"

            // Award new customer
            def award = new Award(
                awardDate: new Date(),
                type: 'REWARD',
                points: 1,
                customer: processedCustomer
            )
            award.save(flush: true)

            welcome = "Welcome, new customer."
        }
        else {
            welcome = "Welcome back ${processedCustomer?.firstName}!"
            def integerPoints = processedCustomer?.awardPoints ?: 0
            if (integerPoints != 1) {
                points = "${integerPoints} points."
            }
        }

        points = "You have " + points

        return [processedCustomer, welcome, points]
    }
}