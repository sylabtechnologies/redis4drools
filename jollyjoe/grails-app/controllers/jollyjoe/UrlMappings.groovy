package jollyjoe

class UrlMappings {

    static mappings = {
        "/checkin"(controller:'customer', action:'checkin')

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:'customer', action:'checkin')
        "/index"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
