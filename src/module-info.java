module richrail{
	requires java.desktop;
	requires java.sql;
    requires transitive javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	
	exports GUItest;
}