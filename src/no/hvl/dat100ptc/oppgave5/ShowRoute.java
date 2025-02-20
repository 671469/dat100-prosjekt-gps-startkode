package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		
		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
			
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		double ystep = MAPYSIZE / (Math.abs(maxlat - minlat));
		
		return ystep;
		
	}

	public void showRouteMap(int ybase) {
		
		for (int i = 0; i < gpspoints.length - 1; i++) {
			
			double xOne = (gpspoints[i].getLongitude() - GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints))) * xstep();
			double yOne = (gpspoints[i].getLatitude() - GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints))) * ystep();
			double xTwo = (gpspoints[i + 1].getLongitude() - GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints))) * xstep();
			double yTwo = (gpspoints[i + 1].getLatitude() - GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints))) * ystep();
			
			//lager en sirkel for hvert punkt og siste sirkel skal være blå
			if (i == gpspoints.length - 2) {
				setColor(0, 0, 255);
				fillCircle(MARGIN + (int) xOne, (int) (ybase - yOne), 7);
			} else {
				setColor(0, 255, 51);
				fillCircle(MARGIN + (int) xOne, (int) (ybase - yOne), 3);
			}

			setColor(22, 100, 8);
			drawLine(MARGIN + (int) xOne, (int)(ybase - yOne), MARGIN + (int) xTwo, (int)(ybase - yTwo));
		}
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);

		String[] stats = gpscomputer.getStatistics();

		for (int i = 0; i < stats.length; i++) {
            drawString(stats[i], MARGIN, MARGIN + (i * TEXTDISTANCE));
        }

	}

}
