import grails.test.AbstractCliTestCase

class SetupConfigTests extends AbstractCliTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSetupConfig() {

        execute(["setup-config"])

        assertEquals 0, waitForProcess()
        verifyHeader()
    }
}
