package de.mvcp;

import de.mvcp.controller.impl.ControllerImpl;
import de.mvcp.view.impl.ViewImpl;

public class Application {

	public static void main(String[] args) {
		javafx.application.Application.launch(ViewImpl.class);
	}

}
