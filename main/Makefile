runFDTests:
	javac -cp .:../junit5.jar  FrontendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests
runBDTests: BackendDeveloperTests.class
	@java -jar ~/junit5.jar -cp . -c BackendDeveloperTests

BackendDeveloperTests.class: BackendDeveloperTests.java
	@javac -cp ~/junit5.jar *.java
runApp:
	javac -cp .:../junit5.jar App.java
	java -jar ../junit5.jar -cp . -c App
clean:
	@rm -f *.class
