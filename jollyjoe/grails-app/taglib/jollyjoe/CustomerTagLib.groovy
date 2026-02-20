package jollyjoe

class CustomerTagLib {
    static defaultEncodeAs = [taglib:'html']

    def phone334 = { attrs, body ->
        String phone = attrs.phone ?: ''
        if (phone.length() == 10) {
            out << phone.replaceFirst(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3')
        } else {
            out << phone
        }
    }
    
}
