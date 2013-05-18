package grails.neo4j

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(PodController)
@Mock(Pod)
class PodControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/pod/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.podInstanceList.size() == 0
        assert model.podInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.podInstance != null
    }

    void testSave() {
        controller.save()

        assert model.podInstance != null
        assert view == '/pod/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/pod/show/1'
        assert controller.flash.message != null
        assert Pod.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/pod/list'

        populateValidParams(params)
        def pod = new Pod(params)

        assert pod.save() != null

        params.id = pod.id

        def model = controller.show()

        assert model.podInstance == pod
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/pod/list'

        populateValidParams(params)
        def pod = new Pod(params)

        assert pod.save() != null

        params.id = pod.id

        def model = controller.edit()

        assert model.podInstance == pod
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/pod/list'

        response.reset()

        populateValidParams(params)
        def pod = new Pod(params)

        assert pod.save() != null

        // test invalid parameters in update
        params.id = pod.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/pod/edit"
        assert model.podInstance != null

        pod.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/pod/show/$pod.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        pod.clearErrors()

        populateValidParams(params)
        params.id = pod.id
        params.version = -1
        controller.update()

        assert view == "/pod/edit"
        assert model.podInstance != null
        assert model.podInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/pod/list'

        response.reset()

        populateValidParams(params)
        def pod = new Pod(params)

        assert pod.save() != null
        assert Pod.count() == 1

        params.id = pod.id

        controller.delete()

        assert Pod.count() == 0
        assert Pod.get(pod.id) == null
        assert response.redirectedUrl == '/pod/list'
    }
}
