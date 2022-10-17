package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int n) {

		this.gpspoints = new GPSPoint[n];
		this.antall = 0;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	protected boolean insertGPS(GPSPoint gpspoint) {

		if (antall < gpspoints.length) {
			gpspoints[antall] = gpspoint;
			antall++;

			return true;
		}

		return false;

	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		GPSPoint gpspoint = GPSDataConverter.convert(time,latitude, longitude, elevation);

		return insertGPS(gpspoint);

	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		for (antall = 0; antall < gpspoints.length; antall++) {
			System.out.println(gpspoints[antall].toString());
		}

		System.out.println("====== Konvertert GPS Data - SLUTT ======");
	}
}
