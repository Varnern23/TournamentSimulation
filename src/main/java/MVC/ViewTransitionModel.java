package MVC;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class ViewTransitionModel implements ViewTransitionModelInterface {
	
	private BorderPane borderPane;
	Model model;
	
	public ViewTransitionModel(BorderPane borderPane, Model model) {
		super();
		this.borderPane = borderPane;
		this.model = model;
	}
	
	@Override
	public void TournamentViewLoad() {
		FXMLLoader tLoader = new FXMLLoader(getClass().getResource("/TournamentsListView.fxml"));
		try {
			borderPane.setCenter(tLoader.load());
			TournamentListController controller = tLoader.getController();
			controller.setModel(model, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void SpectateViewLoad() {
		FXMLLoader sLoader = new FXMLLoader(getClass().getResource("/SpectateTournamentView.fxml"));
		try {
			borderPane.setCenter(sLoader.load());
			SpectateTournamentViewController controller = sLoader.getController();
			controller.setModel(model, this);
			model.spectateTournament();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	

}
