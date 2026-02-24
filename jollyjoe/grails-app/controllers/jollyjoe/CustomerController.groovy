package jollyjoe
import jollyjoe.Customer

class CustomerController {
    def customerService
    // enable dynamic scaffolding for Customer domain
    static scaffold = Customer

    // custom actions live alongside scaffolded ones; override the scaffolded here
    // override the total points calculation
    def show(Long id) {
        def customer = Customer.get(id)
        if (!customer) {
            flash.message = "Customer not found"
            redirect(action: "index")
            return
        }
        else {
            println "Customer found: $customer"
        }

        // Set total points to the count of awards
        def totalPoints = customer.awards?.size() ?: 0
        customer.awardPoints = totalPoints
        [customer: customer]
    }

    /**
     * Render the customer check‑in kiosk form.
     * Mapped from `/checkin` in UrlMappings.
     */
    def checkin() {
        // render view: 'checkin'
    }

    /**
     * kiosk action
     */
    def customerLookup(Customer customerInstance) {
        def processedCustomer = Customer.findByPhone(customerInstance.phone)
        if (!processedCustomer) {
            // New customer: create with dummy info and redirect to profile update
            def (newcustomer, welcome, points) = customerService.lookup(customerInstance.phone)            
            processedCustomer = newcustomer
            redirect(action: 'profile', id: processedCustomer.phone)
            return
        }
        // Existing customer: show welcome view
        processedCustomer.awardPoints = processedCustomer.awards?.size() ?: 0
        render(view: 'welcome', model: [customer: processedCustomer])
    }

    // Render the customer profile view by phone number.
    def profile() {
        def customer = Customer.findByPhone(params.id)
        if (!customer) {
            flash.message = "Customer not found"
            redirect(action: "index")
            return
        }
        // Determine if this is a new customer (missing required profile fields)
        def isNewCustomer = !customer.firstName || !customer.lastName || !customer.email
        if (isNewCustomer) {
            flash.message = "First name, last name, and email are required."
        }
        render(view: 'profile', model: [customer: customer, newCustomer: isNewCustomer])
    }

    def updateProfile() {
        def result = customerService.updateByPhone(
            params.id as Long,
            params.firstName,
            params.lastName,
            params.email
        )
        if (!result.success) {
            flash.message = result.message
            if (result.customer) {
                redirect(action: "profile", id: result.customer.phone)
            } else {
                redirect(action: "index")
            }
            return
        }
        redirect(action: "show", id: result.customer.id)
    }

}

    // SEND CACLULATED POINTS TO THE SCAFFOLDED ACTION
    // def customerLookup(Customer customerInstance) {
    //     // lookup customer
    //     def processedCustomer = Customer.findByPhone(customerInstance.phone)
    //     if (!processedCustomer) {
    //         processedCustomer = new Customer()  // create new customer if not found
    //         processedCustomer.phone = customerInstance.phone
    //     }
    //     render(view: 'show', model: [customer: processedCustomer])
    // }

