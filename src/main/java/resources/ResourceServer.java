package resources;

public class ResourceServer implements ResourceServerI {
    TestResource testResource;

    @Override
    public TestResource getTestResource() {return testResource;}

    @Override
    public void addTestResource(TestResource testResource){
        this.testResource = testResource;
    }
}
