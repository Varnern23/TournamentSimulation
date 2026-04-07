package main;
import java.util.Scanner;

public class HumanBot extends Robot{
	Scanner reader = new Scanner(System.in);
	public HumanBot(String name) {
		super(name);
	}

	@Override
	public String getAction() {
		System.out.println("Give your command S for snitch or D for deny");
		String response = reader.next();
		return response;
	}

}
