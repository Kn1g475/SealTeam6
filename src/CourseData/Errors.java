package CourseData;

import java.util.ArrayList;

/**
 * This is a static utility class that processes the data structure and 1. sets
 * final exam slots (hard coded due to time constraints) and 2. finds and marks
 * conflicts between classes
 * @author matt
 */
public class Errors {
	/**
	 * Hard coded way of setting the final exam slots
	 * @param classList
	 * @param cats
	 * @throws Exception
	 */
	public static void setCategories(ArrayList<Class> classList,
			ArrayList<Category> cats) throws Exception {

		if (cats.size() == 0) {

			/*
			 * +---------------------------------------------+ | Hard coding
			 * categories for testing purposes |
			 * +---------------------------------------------+
			 */

			Category setCat = new Category(0, 1000, 800, "T", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1000, 1130, 800, "R", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1130, 1300, 1245, "T", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1300, 1430, 1245, "R", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1430, 1600, 1500, "T", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1600, 1730, 1500, "R", "TR");
			setCat.addMeetingPattern("T");
			setCat.addMeetingPattern("R");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1730, "T", "TR");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1945, "T", "T");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1945, "R", "R");
			cats.add(setCat);

			setCat = new Category(0, 1000, 800, "W", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1000, 1130, 1015, "F", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1130, 1300, 1015, "W", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1300, 1430, 1245, "M", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1430, 1600, 1500, "W", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1600, 1730, 1500, "M", "MWF");
			setCat.addMeetingPattern("M");
			setCat.addMeetingPattern("W");
			setCat.addMeetingPattern("F");
			setCat.addMeetingPattern("MW");
			setCat.addMeetingPattern("WF");
			setCat.addMeetingPattern("MF");
			setCat.addMeetingPattern("MTWRF");
			setCat.addMeetingPattern("TWRF");
			setCat.addMeetingPattern("MWRF");
			setCat.addMeetingPattern("MTRF");
			setCat.addMeetingPattern("MTWF");
			setCat.addMeetingPattern("MTWR");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1730, "W", "MW");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1945, "M", "M");
			cats.add(setCat);

			setCat = new Category(1730, 2400, 1945, "W", "W");
			cats.add(setCat);
		}
		// loop through data object and set categories
		for (int i = 0; i < classList.size(); i++) {
			for (int j = 0; j < cats.size(); j++) {

				if (cats.get(j).matches(classList.get(i))) {
					classList.get(i).setCategory(cats.get(j));
				}
			}
		}
	}

	/**
	 * method marks classes have overlapping times
	 * 
	 * @param classList
	 * @throws Exception
	 */
	public static void displayError(ArrayList<Class> classList) throws Exception {
		for (int i = 0; i < classList.size(); i++) {
			for (int j = 0; j < classList.size(); j++) {
				if (i == j)
					continue;
				Class a = classList.get(i);
				Class b = classList.get(j);

				if (a.getCategory() == null || b.getCategory() == null)
					throw new Exception();
				if (a.endTime <= b.startTime && a.getCategory() == b.getCategory() && a.getCourse() != b.getCourse()) {
					a.hasConflict = true;
					b.hasConflict = true;
					a.getCategory().hasConflicts = true;
					System.out.println(String.format("There is a conflict!: \n\t%s ----- %s", a.toString(), b.toString()));
				}
			}
		}
	}
}
