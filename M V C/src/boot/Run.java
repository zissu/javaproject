
package boot;

import model.Model;
import model.MyModel;
import controller.Controller;
import controller.MyController;
import view.MyView;
import view.View;

public class Run {

	public static void main(String[] args) {
		Controller controller=new MyController();
		Model model=new MyModel(controller);
		View view=new MyView(controller);
		controller.setM(model);
		controller.setV(view);
		view.start();

	}

}