package rest.form

class Person {

    String username;
    String name;
    String email;
    Date startDate;

    static belongsTo = Pod
    static hasMany = [ pods: Pod ]
    static mapping = {
        id name: ['username']
    }

    @Override
    String toString() {
        return username
    }
}
