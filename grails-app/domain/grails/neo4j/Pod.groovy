package grails.neo4j

class Pod {

    static hasMany = [ containsMember: Person ]

    String name;

}
