package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.CommonModel;
import model.MyModel;
import presenter.Presenter;
import view.CLI;
import view.CommonView;
import view.GUI;

/**
 * main that runs the mvp architecture,according to parameters it got from the xml file
* @author Chen Zissu
* @version 1.0
* @since 2015-12-28
 */
public class Run {

	public static void main(String[] args) {
		
		//constructing model
		CommonModel m = new MyModel(args);
		String s=m.getProperties().getTypeOfUserInterfece();
		CommonView ui;
		//checking if gui or cli selected
		if(s.intern()=="gui")
		{
			ui=new GUI();
		}
		else if(s.intern()=="cli")
		{
			ui=new CLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		}
		else
		{
			ui=new GUI();
		}
		
		Presenter p=new Presenter(m, ui);

		ui.addObserver(p);
		m.addObserver(p);
		ui.start();

	}

}
