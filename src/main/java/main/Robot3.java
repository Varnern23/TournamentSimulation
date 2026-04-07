package main;
public class Robot3 extends Robot {

	public Robot3(String name) {
        super(name);
    }

    public String getAction() {
        double num = Math.random();
        if (num >= 0.5) {
        	return "S";
        }
        else {
        	return "D";
        }
    }
}
